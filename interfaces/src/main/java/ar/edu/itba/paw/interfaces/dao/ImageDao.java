package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;

import java.util.Optional;

public interface ImageDao {

  /**
   * Find image by id
   * @param id
   * @return the image if exists, empty otherwise
   */
  public Optional<Image> findById(final Long id);

  /**
   * Add image to publication
   * @param publication
   * @param base64
   * @return
   */
  public Image addToPublication(final Publication publication, final String base64);
}
