package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
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

    Optional<Order> existantOrder = publication.getOrders().stream().filter(o -> o.getOrderer().equals(loggedUser)).findFirst();

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

    if(publication.get().getFulfilled()) {
      throw new PublicationFulfilledException("Can't delete an order from a fulfilled publication");
    }

    orderDao.deleteById(loggedUser.get().getId(), publicationId);
  }
}
