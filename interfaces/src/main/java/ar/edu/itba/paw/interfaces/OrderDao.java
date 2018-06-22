package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.Order;

public interface OrderDao {
	
	/**
	 * Finds orders with the provided subscriber.
	 * 
	 * @param username The username of the subscriber.
	 * 
	 * @return The orders with the provided subscriber.
	 */
	
	public List<Order> findBySubscriber(final String username);
	
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
	
	public List<Order> findFinalizedBySubscriber(String username);
	
	/**
	 * Creates a new order.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The username of the subscriber.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created order.
	 */
	
	public Order create(final long publication_id, final String subscriber, final int quantity);
	
	/**
	 * Sets the is_confirmed attribute for the provided publication id and subscriber.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The username of the subscriber.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	public boolean confirm(final long publication_id, final String subscriber);
	
	/**
	 * Delete all orders for the corresponding publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(final long publication_id);

	/**
	 * Delete all orders for the corresponding publication id and subscriber.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The subscriber of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(long publication_id, String subscriber);
}