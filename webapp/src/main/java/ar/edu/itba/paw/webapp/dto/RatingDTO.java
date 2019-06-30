package ar.edu.itba.paw.webapp.dto;

public class RatingDTO {
  private Integer rating;

  public RatingDTO() {
    // Empty constructor needed by JAX-RS
  }

  public RatingDTO(Integer rating) {
    this.rating = rating;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }
}
