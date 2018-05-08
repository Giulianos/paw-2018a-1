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
	
	List<Order> findBySubscriber(final String username);
	
	/**
	 * Finds the orders with the provided publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return The orders with the provided publication id.
	 */
	
	List<Order> findByPublicationId(final long publication_id);
	
	/**
	 * Creates a new order.
	 * 
	 * @param publication_id The id of the publication.
	 * @param subscriber The username of the subscriber.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created order.
	 */
	
	Order create(final long publication_id, final String subscriber, final int quantity);
}