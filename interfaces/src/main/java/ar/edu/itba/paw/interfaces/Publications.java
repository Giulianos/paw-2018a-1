package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.Publication;

public interface Publications {
	
	/**
	 * Finds the publication with the provided id.
	 * 
	 * @param id The id of the publication.
	 * @return The publication with the provided id.
	 */
	
	public Publication findById(final long id);
	
	/**
	 * Finds publications with the provided supervisor.
	 * 
	 * @param username The username of the supervisor.
	 * @return The publications with the provided supervisor.
	 */
	
	public List<Publication> findBySupervisor(final String username);
	
	/**
	 * Finds publications that contain or have the exact provided description.
	 * 
	 * @param description The description to match.
	 * @return The publications with the provided description.
	 */
	
	public List<Publication> findByDescription(final String description);
	
	/**
	 * Finds publications that are between the specified price range.
	 * 
	 * @param minPrice The minimum price to look for.
	 * @param maxPrice The maximum price to look for.
	 * @return The publications with the specified price range.
	 */
	
	public List<Publication> findByPrice(final float minPrice, final float maxPrice);
	
	/**
	 * Finds publications with the specified quantity.
	 * 
	 * @param quantity The quantity to look for.
	 * @return The publications with the specified quantity.
	 */
	
	public List<Publication> findByQuantity(final int quantity);
	
	/**
	 * Finds publications with the specified quantity range.
	 * 
	 * @param minQuantity The minimum quantity to look for.
	 * @param maxQuantity The maximum quantity to look for.
	 * @return The publications with the specified quantity range.
	 */
	
	public List<Publication> findByQuantity(final int minQuantity, final int maxQuantity);
	
	/**
	 * Create a new publication.
	 * 
	 * @param supervisor The username of the supervisor.
	 * @param description The description of the publication.
	 * @param price The price of the publication.
	 * @param quantity The quantity of the publication.
	 * 
	 * @return The created publication.
	 */
	
	Publication create(final String supervisor, final String description, final float price, final int quantity);
	
	/**
	 * Finds the quantity ordered for the provided publication id.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return The quantity ordered for the provided publication id.
	 */
	
	int remainingQuantity(final long id);
	
	/**
	 * Verifies if the total quantity for the provided publication_id has been reached.
	 * If true, the database is updated accordingly.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return True if the total quantity for the provided id has been reached.
	 */
	
	boolean confirm(final long id);
	
	/**
	 * Verifies if all orders have been confirmed.
	 * If true, orders are moved to the confirmedOrders database.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return True if all orders have been confirmed.
	 */
	
	boolean confirmOrders(final long id);
	
	/**
	 * Verifies if all confirmed orders have been paid and received.
	 * If true, publication is complete and orders are removed.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return True if all orders have been paid and delivered.
	 */

	boolean confirmFulfillment(long id);
	
	/**
	 * Delete publication for the corresponding id.
	 * 
	 * @param id The id of the publication to be deleted.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	boolean delete(final long id);
}