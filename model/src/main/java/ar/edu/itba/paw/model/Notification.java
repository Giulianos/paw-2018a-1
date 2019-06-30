package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notifiactions")
public class Notification extends TimestampedEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Access(AccessType.PROPERTY)
  private Long id;

  @Column(nullable = false)
  private NotificationType type;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "related_publication_id")
  private Publication relatedPublication;

  @ManyToOne
  @JoinColumn(name = "related_order_id")
  private Order relatedOrder;

  @Column
  private Boolean seen = false;

  public Notification() {
    // Empty constructor needed by Hibernate
  }

  public Notification(NotificationType type, User user, Publication relatedPublication, Order relatedOrder) {
    this.type = type;
    this.relatedPublication = relatedPublication;
    this.relatedOrder = relatedOrder;
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public NotificationType getType() {
    return type;
  }

  public void setType(NotificationType type) {
    this.type = type;
  }

  public Publication getRelatedPublication() {
    return relatedPublication;
  }

  public void setRelatedPublication(Publication relatedPublication) {
    this.relatedPublication = relatedPublication;
  }

  public Boolean getSeen() {
    return seen;
  }

  public void setSeen(Boolean seen) {
    this.seen = seen;
  }

  public Order getRelatedOrder() {
    return relatedOrder;
  }

  public void setRelatedOrder(Order relatedOrder) {
    this.relatedOrder = relatedOrder;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Notification that = (Notification) o;
    return Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
