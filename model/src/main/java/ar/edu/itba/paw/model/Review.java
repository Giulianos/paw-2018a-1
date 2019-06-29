package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.compositepks.OrderId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reviews")
public class Review {
  @EmbeddedId
  OrderId id;

  @OneToOne
  @JoinColumns({
      @JoinColumn(name = "ord_publication_id", nullable = false),
      @JoinColumn(name = "ord_orderer_id", nullable = false)
  })
  @MapsId
  private Order order;

  @Column
  private String comment;

  @Column
  private Integer rating;

  public Review() {
    // Empty constructor needed by Hibernate
  }

  public Review(String comment, Integer rating) {
    this.comment = comment;
    this.rating = rating;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Review review = (Review) o;
    return Objects.equals(order, review.order);
  }

  @Override
  public int hashCode() {
    return Objects.hash(order);
  }
}
