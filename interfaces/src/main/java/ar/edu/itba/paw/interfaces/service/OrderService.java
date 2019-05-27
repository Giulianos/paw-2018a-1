package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface OrderService {

  /**
   * Create an order for logged user in publication
   * @param publication
   * @param quantity
   * @return created order
   */
  public Order create(final Publication publication, final Long quantity);

  /**
   * Retreives a paginated list of user's orders
   * @param user the orderer
   * @param page the page number
   * @param pageSize the size of the page
   * @return the paginated list
   */
  public List<Order> userOrders(final User user, final Integer page, final Integer pageSize) throws UnauthorizedAccessException;

  /**
   * Retreives user's orders quantity
   * @param user
   * @return
   */
  public Long userOrdersQuantity(final User user);

}
