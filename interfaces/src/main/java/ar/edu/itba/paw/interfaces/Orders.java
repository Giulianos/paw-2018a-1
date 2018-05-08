package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.Order;

public interface Orders {
	
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
	
	/**
	 * Finds the quantity ordered for the provided publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return The quantity ordered for the provided publication id.
	 */
	
	int quantity(final long publication_id);
	
	/**
	 * Verifies if all orders for the provided publication id are confirmed.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if all orders are confirmed for the provided publication id.
	 */
	
	boolean areConfirmed(final long publication_id);
}