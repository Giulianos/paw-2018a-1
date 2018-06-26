package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface OrderService {
	
	/**
	 * Finds orders with the provided subscriber.
	 * 
	 * @param username The username of the subscriber.
	 * 
	 * @return The orders with the provided subscriber.
	 */
	
	public List<Order> findBySubscriber(final String username);
	
	/**
	 * Finds an order of a subscriber on a publication
	 * @param publication_id The publication of the order
	 * @param username The subscriber of the order
	 * @return If any, the obtained order.
	 */
	public Optional<Order> findByPublicationAndSubscriber(final Long publication_id, final String username);
	
	/**
	 * Finds orders with the provided subscriber.
	 * 
	 * @param username The username of the subscriber.
	 * 
	 * @param withFinalized True if orders for finalized publications should be included.
	 * 
	 * @return The orders with the provided subscriber.
	 */
	
	public List<Order> findBySubscriber(final String username, final boolean withFinalized);
	
	/**
	 * Finds the orders with the provided publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return The orders with the provided publication id.
	 */
	
	public List<Order> findByPublicationId(final long publication_id);
	
	/**
	 * Finds finalized orders with the provided subscriber.
	 * 
	  * @param username The username of the subscriber.
	 * 
	 * @return The finalized orders with the provided subscriber.
	 */

	public List<Order> findFinalizedBySubscriber(String subscriber);
	
	/**
	 * Creates a new order.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The username of the subscriber.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created order.
	 */
	
	public Order create(Publication publication, User subscriber, final int quantity);
	
	/**
	 * Verifies if all orders for the provided publication id are confirmed.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if all orders are confirmed for the provided publication id.
	 */
	
	public boolean areConfirmed(final long publication_id);
	
	/**
	 * Delete the order for the corresponding publication id and subscriber.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The subscriber of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(long publication_id, String subscriber);
	
	/**
	 * Delete all orders for the corresponding publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(final long publication_id);
	
	/**
	 * Checks if any of the provided orders has no supervisor.
	 * 
	 * @param orders The orders to check.
	 * 
	 * @return true if any of the orders has no supervisor.
	 */
	public boolean anyHasNoSupervisor(List<Order> orders);
	
	/**
	 * sets reputation for the supervisor of the order
	 * 
	 * @param order Order to be updated
	 * 
	 * @param reputation The reputation to be set
	 */
	public void setSupervisorReputation(Order order, Integer reputation);
	
	/**
	 * sets reputation for the subscriber of the order
	 * 
	 * @param order Order to be updated
	 * 
	 * @param reputation The reputation to be set
	 */
	public void setSupervisorSubscriber(Order order, Integer reputation);
}