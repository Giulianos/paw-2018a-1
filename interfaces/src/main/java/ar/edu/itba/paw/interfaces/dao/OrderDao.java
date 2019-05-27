package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

  /**
   * Finds the order of user in publication
   *
   * @param publication
   * @param orderer
   * @return found order or empty
   */
  public Optional<Order> find(final Publication publication, final User orderer);

  /**
   * Create an order for user in publication
   * @param publication
   * @param orderer
   * @param quantity
   * @return created order
   */
  public Order create(final Publication publication, final User orderer, final Long quantity);

  /**
   * Retreives a paginated list of user's orders
   * @param user the orderer
   * @param page the page number
   * @param pageSize the size of the page
   * @return the paginated list
   */
  public List<Order> userOrders(final User user, final Integer page, final Integer pageSize);

  /**
   * Retreives user's orders quantity
   * @param user
   * @return
   */
  public Long userOrdersQuantity(final User user);

  /**
   * Update order
   * @param order
   */
  public void update(final Order order);

}
