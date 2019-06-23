package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.compositepks.OrderId;

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
   * Finds the order by id
   * @param id
   * @return found order or empty
   */
  public Optional<Order> findById(final OrderId id);


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
   * Retrieves a paginated list of publication's orders
   * @param publication the publication
   * @param page the page number
   * @param pageSize the size of the page
   * @return the paginated list
   */
  public List<Order> publicationOrders(final Publication publication, final Integer page, final Integer pageSize);

  /**
   * Retrieves publication's orders quantity
   * @param publication
   * @return
   */
  public Long publicationOrdersQuantity(final Publication publication);

  /**
   * Update order
   * @param order
   */
  public void update(final Order order);

  /**
   * Delete order by id
   * @param ordererId
   * @param publicationId
   */
  public void deleteById(final Long ordererId, final Long publicationId);

}
