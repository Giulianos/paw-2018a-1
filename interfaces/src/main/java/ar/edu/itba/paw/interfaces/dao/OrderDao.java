package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

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
   * Update order
   * @param order
   */
  public void update(final Order order);

}
