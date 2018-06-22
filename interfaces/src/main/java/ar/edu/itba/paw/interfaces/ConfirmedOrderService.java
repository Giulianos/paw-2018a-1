package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.ConfirmedOrder;

public interface ConfirmedOrderService {
	
	/**
	 * Finds orders with the provided buyer.
	 * 
	 * @param username The username of the buyer.
	 * 
	 * @return The orders with the provided buyer.
	 */
	
	List<ConfirmedOrder> findByBuyer(final String username);
	
	/**
	 * Finds the confirmed orders with the provided publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return The confirmed orders with the provided publication id.
	 */
	
	List<ConfirmedOrder> findByPublicationId(final long publication_id);
	
	/**
	 * Creates a new confirmed order.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created confirmed order.
	 */
	
	ConfirmedOrder create(final long publication_id, final String buyer, final int quantity);
	
	/**
	 * Sets the is_paid attribute for the provided publication id and buyer.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	boolean confirmPayment(final long publication_id, final String buyer);
	
	/**
	 * Sets the is_received attribute for the provided publication id and buyer.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	boolean confirmDelivery(final long publication_id, final String buyer);
	
	/**
	 * Verifies if all orders for the provided publication id are paid and delivered.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if all orders are paid and received for the provided publication id.
	 */
	
	boolean areFulfilled(final long publication_id);
	
	/**
	 * Delete all orders for the corresponding publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	boolean delete(final long publication_id);
}