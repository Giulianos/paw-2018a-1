package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "messages")
public class Message extends TimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Access(AccessType.PROPERTY)
  private Long id;

  @ManyToOne
  @Access(AccessType.PROPERTY)
  @JoinColumns({
      @JoinColumn(name = "ord_publication_id", insertable = false, updatable = false),
      @JoinColumn(name = "ord_orderer_id", insertable = false, updatable = false)
  })
  private Order order;

  @Column
  private String message;

  @Column(name = "to_supervisor")
  private Boolean toSupervisor;

  public Message() {
    // needed by Hibernate
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Boolean getToSupervisor() {
    return toSupervisor;
  }

  public void setToSupervisor(Boolean toSupervisor) {
    this.toSupervisor = toSupervisor;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Message message = (Message) o;
    return Objects.equals(id, message.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
