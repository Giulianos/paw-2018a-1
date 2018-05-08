package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.ConfirmedOrder;

public interface ConfirmedOrderDao {
	
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
}