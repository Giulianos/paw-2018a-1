package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.compositepks.OrderId;

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
   * Find order by
   */

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

  /**
   * Retrieves a paginated list of publication's orders
   * @param publication the publication
   * @param page the page number
   * @param pageSize the size of the page
   * @return the paginated list
   */
  public List<Order> publicationOrders(final Publication publication, final Integer page, final Integer pageSize) throws UnauthorizedAccessException;

  /**
   * Retrieves publication's orders quantity
   * @param publication
   * @return
   */
  public Long publicationOrdersQuantity(final Publication publication);

  /**
   * Delete order by publication id
   * @param publicationId
   */
  public void deleteByPublicationId(final Long publicationId) throws PublicationFulfilledException, EntityNotFoundException;

  /**
   * Retrieves order messages
   * @param id
   * @return The list of messages
   */
  public List<Message> getOrderMessagesById(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Retrieves the unseen messages for logged user in order
   * @param id
   * @return The list of messages
   */
  public List<Message> getOrderUnseenMessages(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Marks logged user messages in order as seen
   * @param id
   * @throws EntityNotFoundException
   * @throws UnauthorizedAccessException
   */
  public void markMessagesAsSeen(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Sends a message to an order from logged user
   * @param id
   * @param message
   */
  public Message sendMessage(final OrderId id, final String message) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Confirms that the supervisor purchased the order
   * @param id
   */
  public void confirmOrderPurchase(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Returns true if the logged user reviewed the order
   * @param id
   * @return
   */
  public Boolean didReviewOrder(final OrderId id) throws EntityNotFoundException, UnauthorizedAccessException;

}
