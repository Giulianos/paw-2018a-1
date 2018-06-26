package ar.edu.itba.paw.interfaces;

import java.util.List;
import java.util.Optional;

import javax.activation.UnsupportedDataTypeException;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface PublicationDao {
	
	/**
	 * Finds the publication with the provided id.
	 * 
	 * @param id The id of the publication.
	 * 
	 * @return The publication with the provided id.
	 */
	
	public Optional<Publication> findById(final long id);
	
	/**
	 * Finds publications with the provided supervisor.
	 * 
	 * @param supervisor The supervisor.
	 * 
	 * @return The publications with the provided supervisor.
	 */
	
	public List<Publication> findBySupervisor(User supervisor);
	
	/**
	 * Finds publications that contain or have the exact provided description.
	 * 
	 * @param description The description to match.
	 * 
	 * @return The publications with the provided description.
	 */
	
	public List<Publication> findByDescription(final String description);

	/**
	 * Finds publications that contain or have the exact provided description.
	 * 
	 * Only returns the publications in which supervisor is not null
	 * 
	 * @param description The description to match.
	 * 
	 * @return The publications with the provided description.
	 */
	
	public List<Publication> findByDescription(String description, boolean checkSupervisor);
	
	/**
	 * Finds publications that are between the specified price range.
	 * 
	 * @param minPrice The minimum price to look for.
	 * @param maxPrice The maximum price to look for.
	 * 
	 * @return The publications with the specified price range.
	 */
	
	public List<Publication> findByPrice(final float minPrice, final float maxPrice);
	
	/**
	 * Finds publications with the specified quantity.
	 * 
	 * @param quantity The quantity to look for.
	 * 
	 * @return The publications with the specified quantity.
	 */
	
	public List<Publication> findByQuantity(final int quantity);
	
	/**
	 * Finds publications with the specified quantity range.
	 * 
	 * @param minQuantity The minimum quantity to look for.
	 * @param maxQuantity The maximum quantity to look for.
	 * 
	 * @return The publications with the specified quantity range.
	 */
	
	public List<Publication> findByQuantity(final int minQuantity, final int maxQuantity);
	
	/**
	 * Creates a new publication.
	 * 
	 * @param supervisor The supervisor.
	 * @param description The description of the publication.
	 * @param price The price of the publication.
	 * @param quantity The quantity of the publication.
	 * @param image The publication image encoded in base64
	 * @param tags The tags to find the publication
	 * 
	 * @return The created user.
	 */
	
	public Publication create(User supervisor, final String description, final float price, final int quantity, final String image, final String tags);
	
	/**
	 * Sets the is_confirmed attribute for the provided id.
	 * 
	 * @param publication The publication.
	 * 
	 * @return True if the set was successful.
	 * 
	 */
	
	public boolean confirm(Publication publication);
	
	/**
	 * Delete publication.
	 * 
	 * @param publication The publication to be deleted.
	 * 
	 * @return True if the deletion was successful.
	 */
	
	public boolean delete(Publication publication);
	
	/**
	 * Sets a new supervisor for the corresponding publication.
	 * 
	 * @param supervisor The new supervisor
	 * 
	 * @param publication The publication
	 * 
	 * @return True if the set was successful.
	 */
	
	public boolean setNewSupervisor (User supervisor, Publication publication);
	
	/**
	 * Checks if supervisor is null for the corresponding publication.
	 * 
	 * @param publication The publication.
	 * 
	 * @return True if the set was successful.
	 */
	
	public boolean hasSupervisor (Publication publication);
	
	/**
	 * Updates the publication object.
	 * 
	 * @param publication The publication to be updated.
	 * 
	 * @return true if the update was successful.
	 */
	public boolean updatePublication(Publication publication);

}