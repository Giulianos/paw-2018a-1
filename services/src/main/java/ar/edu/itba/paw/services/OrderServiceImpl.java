package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.MessageDao;
import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.compositepks.OrderId;
import ar.edu.itba.paw.model.events.NewMessageEvent;
import ar.edu.itba.paw.model.events.OrderConfirmedEvent;
import ar.edu.itba.paw.model.events.PublicationFulfilledEvent;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.parser.Entity;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;
  @Autowired
  private PublicationDao publicationDao;
  @Autowired
  private UserService userService;
  @Autowired
  private ApplicationEventPublisher eventPublisher;
  @Autowired
  private MessageDao messageDao;

  @Override
  public Order create(Publication publication, Long quantity) {
    SecurityContext securityContext = SecurityContextHolder.getContext();

    Optional<User> loggedUser = userService.findByEmail(
        securityContext.getAuthentication().getName()
    );

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed create order");
    }

    if(publication.getAvailableQuantity() < quantity) {
      throw new IllegalArgumentException("Order quantity should be le than available quantity");
    }

    Optional<Order> existantOrder = publication.getOrders().stream().filter(o -> o.getOrderer().equals(loggedUser.get())).findFirst();

    if(existantOrder.isPresent()) {
      /** The user already ordered from this publication, so we just update the quantity */
      final Long newQuantity = existantOrder.get().getQuantity() + quantity;
      existantOrder.get().setQuantity(newQuantity);
      orderDao.update(existantOrder.get());
    } else {
      /** Create a new order */
      existantOrder = Optional.of(orderDao.create(publication, loggedUser.get(), quantity));
    }

    if(publication.getAvailableQuantity().equals(0L)) {
      // The user ordered all remaining products (publication fulfilled)
      eventPublisher.publishEvent(new PublicationFulfilledEvent(publication));
    }

    return existantOrder.get();
  }

  @Override
  public List<Order> userOrders(User user, Integer page, Integer pageSize) throws UnauthorizedAccessException {
    if (!user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
      throw new UnauthorizedAccessException("Can't access other user's orders");
    }

    return orderDao.userOrders(user, page, pageSize);
  }

  @Override
  public Long userOrdersQuantity(User user) {
    return orderDao.userOrdersQuantity(user);
  }

  @Override
  public List<Order> publicationOrders(Publication publication, Integer page, Integer pageSize) throws UnauthorizedAccessException {
    if(!publication.getSupervisor().getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
      throw new UnauthorizedAccessException("Only the supervisor can see the orders");
    }

    return orderDao.publicationOrders(publication, page, pageSize);
  }

  @Override
  public Long publicationOrdersQuantity(Publication publication) {
    return orderDao.publicationOrdersQuantity(publication);
  }

  @Override
  @Transactional
  public void deleteByPublicationId(final Long publicationId) throws PublicationFulfilledException, EntityNotFoundException {
    Optional<Publication> publication = publicationDao.findById(publicationId);
    Optional<User> loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if(!publication.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed delete order");
    }

    PublicationState publicationState = publication.get().getState();

    if(!publicationState.equals(PublicationState.IN_PROGRESS) && !publicationState.equals(PublicationState.ORPHAN)) {
      throw new PublicationFulfilledException("Can't delete an order from a fulfilled publication");
    }

    orderDao.deleteById(loggedUser.get().getId(), publicationId);
  }

  @Override
  @Transactional
  public List<Message> getOrderMessagesById(OrderId id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(id);
    Optional<User> loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed messages");
    }

    if(!order.get().getOrderer().equals(loggedUser.get()) && !order.get().getPublication().getSupervisor().equals(loggedUser.get())) {
      throw new UnauthorizedAccessException("Only orderer or supervisor can see order messages");
    }

    return new LinkedList<>(order.get().getMessages());
  }

  @Override
  @Transactional
  public List<Message> getOrderUnseenMessages(OrderId id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(id);
    Optional<User> loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed messages");
    }

    if(!order.get().getOrderer().equals(loggedUser.get()) && !order.get().getPublication().getSupervisor().equals(loggedUser.get())) {
      throw new UnauthorizedAccessException("Only orderer or supervisor can see unseen messages");
    }

    return messageDao.getUnread(order.get(), loggedUser.get());
  }

  @Override
  public void markMessagesAsSeen(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(id);
    Optional<User> loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed messages");
    }

    if(!order.get().getOrderer().equals(loggedUser.get()) && !order.get().getPublication().getSupervisor().equals(loggedUser.get())) {
      throw new UnauthorizedAccessException("Only orderer or supervisor can mark messages");
    }

    List<Message> unseenMessages = messageDao.getUnread(order.get(), loggedUser.get());

    messageDao.markRead(unseenMessages);
  }

  @Override
  @Transactional
  public Message sendMessage(OrderId id, String message) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(id);
    Optional<User> loggedUser = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed messages");
    }

    if(!order.get().getOrderer().equals(loggedUser.get()) && !order.get().getPublication().getSupervisor().equals(loggedUser.get())) {
      throw new UnauthorizedAccessException("Only orderer or supervisor can send messages");
    }

    User receiver = order.get().getOrderer().equals(loggedUser.get()) ? order.get().getPublication().getSupervisor() : order.get().getOrderer();

    Message sentMessage = messageDao.createMessage(order.get(), loggedUser.get(), receiver, message);
    orderDao.update(order.get());

    // Publish event (for notifications)
    eventPublisher.publishEvent(new NewMessageEvent(sentMessage));

    return sentMessage;
  }

  @Override
  @Transactional
  public void confirmOrderPurchase(OrderId id) throws EntityNotFoundException, UnauthorizedAccessException {
    Optional<Order> order = orderDao.findById(id);
    String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!order.get().getOrderer().getEmail().equals(loggedUserEmail)) {
      throw new UnauthorizedAccessException("Only the orderer can confirm the purchase");
    }

    if(order.get().getPublication().getState() != PublicationState.PURCHASED) {
      throw new IllegalStateException();
    }

    order.get().setPurchaseAccepted(true);
    orderDao.update(order.get());

    eventPublisher.publishEvent(new OrderConfirmedEvent(order.get()));
  }

  @Override
  @Transactional
  public Boolean didReviewOrder(OrderId id) throws EntityNotFoundException, UnauthorizedAccessException {
    final String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
    Optional<Order> order = orderDao.findById(id);

    if(!order.isPresent()) {
      throw new EntityNotFoundException();
    }

    if(!order.get().getOrderer().getEmail().equals(loggedUserEmail)) {
      throw new UnauthorizedAccessException("Only the orderer can access this information");
    }

    return order.get().getReview() != null;
  }
}
