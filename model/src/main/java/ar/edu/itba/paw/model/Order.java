package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.compositepks.OrderId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "orders")
public class Order extends TimestampedEntity {

  @EmbeddedId
  private OrderId id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "publication_id", insertable = false, updatable = false)
  private Publication publication;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "orderer_id", insertable = false, updatable = false)
  private User orderer;

  @Column
  private Long quantity;

  public Order() {
    // Needed by Hibernate
  }

  public Order(Publication publication, User orderer, Long quantity) {
    this.id = new OrderId(orderer.getId(), publication.getId());
    this.publication = publication;
    this.orderer = orderer;
    this.quantity = quantity;
  }

  public OrderId getId() {
    return id;
  }

  public void setId(OrderId id) {
    this.id = id;
  }

  public Publication getPublication() {
    return publication;
  }

  public void setPublication(Publication publication) {
    this.publication = publication;
  }

  public User getOrderer() {
    return orderer;
  }

  public void setOrderer(User orderer) {
    this.orderer = orderer;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
}
