package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Tag;

import java.util.List;

public interface TagService {

  /**
   * Finds tags starting with keyword
   * @param keyword
   * @param quantity max results quantity
   * @return a list with the results
   */
  public List<Tag> list(final String keyword, final Integer quantity);

}
