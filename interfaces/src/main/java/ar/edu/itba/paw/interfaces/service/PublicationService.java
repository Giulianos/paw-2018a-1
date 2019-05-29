package ar.edu.itba.paw.interfaces.service;

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

}
