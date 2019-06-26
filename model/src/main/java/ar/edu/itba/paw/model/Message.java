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
      @JoinColumn(name = "ord_publication_id", nullable = false),
      @JoinColumn(name = "ord_orderer_id", nullable = false)
  })
  private Order order;

  @Column
  private String message;

  @Column(name = "read_by_receiver")
  private Boolean readByReceiver;

  @ManyToOne(fetch = FetchType.EAGER)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "sender_id")
  private User sender;

  @ManyToOne(fetch = FetchType.EAGER)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "receiver_id")
  private User receiver;

  public Message() {
    // needed by Hibernate
  }

  public Message(Order order, String message, Boolean readByReceiver, User sender, User receiver) {
    this.order = order;
    this.message = message;
    this.readByReceiver = readByReceiver;
    this.sender = sender;
    this.receiver = receiver;
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

  public Boolean getReadByReceiver() {
    return readByReceiver;
  }

  public void setReadByReceiver(Boolean readByReceiver) {
    this.readByReceiver = readByReceiver;
  }

  public User getSender() {
    return sender;
  }

  public void setSender(User sender) {
    this.sender = sender;
  }

  public User getReceiver() {
    return receiver;
  }

  public void setReceiver(User receiver) {
    this.receiver = receiver;
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
