package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Tag;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TagDao {

  /**
   * Creates the passed tag
   * @param tag
   * @return the created tag
   */
  public Tag createTag(final String tag);

  /**
   * List at most maxQuantity tags starting with startingString
   * @param startingString
   * @param maxQuantity
   * @return the tag list
   */
  public List<Tag> list(final String startingString, final Integer maxQuantity);

}
