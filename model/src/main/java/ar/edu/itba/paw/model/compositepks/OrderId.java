package ar.edu.itba.paw.model.compositepks;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderId implements Serializable {

  @Column(name = "orderer_id")
  protected Long ordererId;

  @Column(name = "publication_id")
  protected Long publicationId;

  public OrderId(Long ordererId, Long publicationId) {
    this.ordererId = ordererId;
    this.publicationId = publicationId;
  }

  public OrderId() {
  }

  public Long getOrdererId() {
    return ordererId;
  }

  public Long getPublicationId() {
    return publicationId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    OrderId orderId = (OrderId) o;
    return ordererId.equals(orderId.ordererId) &&
        publicationId.equals(orderId.publicationId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ordererId, publicationId);
  }
}