package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Primary
@Service
public class OrderServiceImpl implements OrderService {

  @Autowired
  private OrderDao orderDao;
  @Autowired
  private UserService userService;

  @Override
  public Order create(Publication publication, Long quantity) {
    SecurityContext securityContext = SecurityContextHolder.getContext();

    Optional<User> loggedUser = userService.findByEmail(
        securityContext.getAuthentication().getName()
    );

    if(!loggedUser.isPresent()) {
      throw new IllegalStateException("User is not logged in but it accessed create order");
    }

    Optional<Order> foundOrder = orderDao.find(publication, loggedUser.get());

    if(foundOrder.isPresent()) {
      /** The user already ordered from this publication, so we just update the quantity */
      final Long newQuantity = foundOrder.get().getQuantity() + quantity;
      foundOrder.get().setQuantity(newQuantity);
      orderDao.update(foundOrder.get());

      return foundOrder.get();
    } else {
      /** Create a new order */
      return orderDao.create(publication, loggedUser.get(), quantity);
    }
  }
}
