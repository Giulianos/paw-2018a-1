package ar.edu.itba.paw.interfaces;

import java.util.List;

import ar.edu.itba.paw.model.Publication;

public interface PublicationDao {
	
	/**
	 * Finds the publication with the provided id.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return The publication with the provided id.
	 */
	
	Publication findById(final long id);
	
	/**
	 * Finds publications with the provided supervisor.
	 * 
	 * @param username The username of the supervisor.
	 * 
	 * @return The publications with the provided supervisor.
	 */
	
	List<Publication> findBySupervisor(final String username);
	
	/**
	 * Finds publications that contain or have the exact provided description.
	 * 
	 * @param description The description to match.
	 * 
	 * @return The publications with the provided description.
	 */
	
	List<Publication> findByDescription(final String description);
	
	/**
	 * Finds publications that are between the specified price range.
	 * 
	 * @param minPrice The minimum price to look for.
	 * @param maxPrice The maximum price to look for.
	 * 
	 * @return The publications with the specified price range.
	 */
	
	List<Publication> findByPrice(final float minPrice, final float maxPrice);
	
	/**
	 * Finds publications with the specified quantity.
	 * 
	 * @param quantity The quantity to look for.
	 * 
	 * @return The publications with the specified quantity.
	 */
	
	List<Publication> findByQuantity(final int quantity);
	
	/**
	 * Finds publications with the specified quantity range.
	 * 
	 * @param minQuantity The minimum quantity to look for.
	 * @param maxQuantity The maximum quantity to look for.
	 * 
	 * @return The publications with the specified quantity range.
	 */
	
	List<Publication> findByQuantity(final int minQuantity, final int maxQuantity);
	
	/**
	 * Creates a new publication.
	 * 
	 * @param supervisor The username of the supervisor.
	 * @param description The description of the publication.
	 * @param price The price of the publication.
	 * @param quantity The quantity of the publication.
	 * 
	 * @return The created user.
	 */
	
	Publication create(final String supervisor, final String description, final float price, final int quantity);
	
	/**
	 * Sets the is_confirmed attribute for the provided id.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return True if it the confirmation update was successful.
	 */
	
	boolean confirm(final long id);
	
	/**
	 * Delete publication for the corresponding id.
	 * 
	 * @param id The id of the publication to be deleted.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	boolean delete(final long id);
}