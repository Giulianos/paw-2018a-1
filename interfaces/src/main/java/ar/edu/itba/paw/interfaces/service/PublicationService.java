package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface PublicationService {

  /**
   * Find the publication with the provided id
   *
   * @param id
   * @return The publication or empty if it was not found
   */
  public Optional<Publication> findById(final Long id);

  /**
   * Retrieve logged user publications
   * @return
   */
  public List<Publication> userPublications() throws UnauthorizedAccessException;

  /**
   * Retrieve latest publications
   * @param quantity
   * @return
   */
  public List<Publication> latest(final Integer quantity);

  /**
   * Creates a new publication
   *
   * @param description
   * @param unitPrice
   * @param quantity
   * @param detailedDescription
   * @return The created publication
   */
  public Publication create(
      final String description,
      final Double unitPrice,
      final Long quantity,
      final String detailedDescription,
      final List<String> tags
  );

  /**
   * Adds an image to a publication
   *
   * @param userEmail the email of the user trying to add the image
   * @param publicationId
   * @param base64
   *
   * @throws EntityNotFoundException if the publication doesn't exist
   * @throws UnauthorizedAccessException if the user is not the supervisor
   *
   * @return the added image entity, empty if the image could not be added
   */
  public Optional<Image> addImage(final String userEmail, final Long publicationId, final String base64) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * The supervisor leaves the publication
   * @param id
   */
  public void leavePublication(final Long id) throws EntityNotFoundException, UnauthorizedAccessException, PublicationFulfilledException;

  /**
   * Adopts an orphan publication
   * @param id
   * @throws EntityNotFoundException
   * @throws UnauthorizedAccessException
   */
  public void adoptPublication(final Long id) throws EntityNotFoundException, UnauthorizedAccessException;

  /**
   * Marks the publication as purchased
   * @param id
   * @throws EntityNotFoundException
   * @throws UnauthorizedAccessException
   */
  public void markAsPurchased(final Long id) throws EntityNotFoundException, UnauthorizedAccessException;

  public Page<Publication> search(final String terms, Integer page, Integer pageSize);
}
