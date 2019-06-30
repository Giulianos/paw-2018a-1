package ar.edu.itba.paw.model;

import ar.edu.itba.paw.model.compositepks.OrderId;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends TimestampedEntity {

  @EmbeddedId
  private OrderId id;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "publication_id", insertable = false, updatable = false)
  private Publication publication;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "orderer_id", insertable = false, updatable = false)
  private User orderer;

  @OneToMany(
      mappedBy = "order",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.LAZY
  )
  @Access(AccessType.PROPERTY)
  private Set<Message> messages = new HashSet<>();

  @OneToOne(
      mappedBy = "order",
      cascade = CascadeType.ALL,
      orphanRemoval = true,
      fetch = FetchType.EAGER
  )
  private Review review;

  @Column
  private Long quantity;

  @Column
  private Boolean purchaseAccepted = false;

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

  public Set<Message> getMessages() {
    return messages;
  }

  public void setMessages(Set<Message> messages) {
    this.messages = messages;
  }

  public Boolean getPurchaseAccepted() {
    return purchaseAccepted;
  }

  public void setPurchaseAccepted(Boolean purchaseAccepted) {
    this.purchaseAccepted = purchaseAccepted;
  }

  public void addMessage(final Message message) {
    this.messages.add(message);
    message.setOrder(this);
  }

  public void removeMessage(final Message message) {
    this.messages.remove(message);
    message.setOrder(null);
  }

  public Review getReview() {
    return review;
  }

  public void setReview(Review review) {
    this.review = review;
    review.setOrder(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Order order = (Order) o;
    return id.equals(order.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
