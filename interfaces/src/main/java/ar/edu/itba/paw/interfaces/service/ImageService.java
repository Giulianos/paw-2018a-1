package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;

import java.util.Optional;

public interface ImageService {

  /**
   * Finds image by id
   * @param id
   * @return the image if found, empty otherwise
   */
  public Optional<Image> findById(final Long id);

}
