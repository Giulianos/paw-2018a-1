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
	
	public List<ConfirmedOrder> findByBuyer(final String username);
	
	/**
	 * Finds the confirmed orders with the provided publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return The confirmed orders with the provided publication id.
	 */
	
	public List<ConfirmedOrder> findByPublicationId(final long publication_id);
	
	/**
	 * Creates a new confirmed order.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * @param quantity The quantity of the order.
	 * 
	 * @return The created confirmed order.
	 */
	
	public ConfirmedOrder create(final long publication_id, final String buyer, final int quantity);
	
	/**
	 * Sets the is_paid attribute for the provided publication id and buyer.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	public boolean confirmPayment(final long publication_id, final String buyer);
	
	/**
	 * Sets the is_received attribute for the provided publication id and buyer.
	 * 
	 * @param publication_id The id of the publication.
	 * @param buyer The username of the buyer.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	public boolean confirmDelivery(final long publication_id, final String buyer);
	
	/**
	 * Delete all orders for the corresponding publication id.
	 * 
	 * @param publication_id The id of the publication.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(final long publication_id);
}