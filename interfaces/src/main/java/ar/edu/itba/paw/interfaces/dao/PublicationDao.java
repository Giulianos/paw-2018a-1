package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface PublicationDao {

    /**
     * Finds the publication with the provided id
     *
     * @param id
     * @return The publication or empty if it was not found
     */
    public Optional<Publication> findById(final Long id);

    /**
     * Create a new publication
     *
     * @param supervisor
     * @param description
     * @param unitPrice
     * @param quantity
     * @param detailedDescription
     * @return The created publication
     */
    public Publication create(final User supervisor, final String description, final Double unitPrice, final Long quantity, final String detailedDescription);
}
