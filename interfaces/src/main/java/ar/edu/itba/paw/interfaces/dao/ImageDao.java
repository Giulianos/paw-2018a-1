package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;

public interface ImageDao {

  /**
   * Add image to publication
   * @param publication
   * @param base64
   * @return
   */
  public Image addToPublication(final Publication publication, final String base64);
}
