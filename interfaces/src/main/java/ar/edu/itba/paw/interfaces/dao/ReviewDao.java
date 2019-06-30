package ar.edu.itba.paw.interfaces.dao;

import ar.edu.itba.paw.model.Review;

import java.util.List;

public interface ReviewDao {

  /**
   * Retrieves the reviews of user
   * @param userId
   * @return The list of reviews
   */
  public List<Review> userReviews(final Long userId);
}
