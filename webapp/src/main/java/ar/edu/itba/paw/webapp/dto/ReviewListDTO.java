package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewListDTO {
  private List<ReviewDTO> reviews;

  public ReviewListDTO() {
    // Empty constructor needed by JAX-RS
  }

  public List<ReviewDTO> getReviews() {
    return reviews;
  }

  public void setReviews(List<ReviewDTO> reviews) {
    this.reviews = reviews;
  }

  public ReviewListDTO(List<Review> reviews) {
    this.reviews = reviews.stream().map(ReviewDTO::new).collect(Collectors.toList());
  }
}
