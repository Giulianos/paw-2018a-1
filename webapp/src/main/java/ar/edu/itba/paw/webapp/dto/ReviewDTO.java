package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Review;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewDTO {
  @Size(min = 0, max = 100)
  @NotNull
  private String comment;

  @Max(5)
  @Min(1)
  @NotNull
  private Integer rating;

  public ReviewDTO() {
    // Empty constructor needed by JAX-RS
  }

  public ReviewDTO(Review review) {
    this.comment = review.getComment();
    this.rating = review.getRating();
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}
