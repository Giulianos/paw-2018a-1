package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

import java.util.List;
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
     * Retrieve user publications
     * @param email
     * @return
     */
    public List<Publication> userPublications(final String email);

    /**
     * Retrieve latest quantity publications
     * @param quantity
     * @return
     */
    public List<Publication> latestPublications(final Integer quantity);

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

    /**
     * Update publication
     * @param publication
     */
    public void update(final Publication publication);

    /**
     * Delete publication by id
     * @param id
     */
    public void deleteById(final Long id);

    /**
     * Retrieve publications by tags (ordered by matches)
     * @param tags
     * @return
     */
    public List<Publication> searchByTags(final List<String> tags, Integer page, Integer pageSize);
}
