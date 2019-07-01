package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Notification;

public class NotificationDTO {
  private String type;
  private OrderDTO relatedOrder;
  private PublicationDTO relatedPublication;

  public NotificationDTO() {
    // Empty constructor needed by JAX-RS
  }

  public NotificationDTO(final Notification notification) {
    this.type = notification.getType().toString();
    this.relatedOrder = new OrderDTO(notification.getRelatedOrder());
    this.relatedPublication = new PublicationDTO(notification.getRelatedPublication());
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public OrderDTO getRelatedOrder() {
    return relatedOrder;
  }

  public void setRelatedOrder(OrderDTO relatedOrder) {
    this.relatedOrder = relatedOrder;
  }

  public PublicationDTO getRelatedPublication() {
    return relatedPublication;
  }

  public void setRelatedPublication(PublicationDTO relatedPublication) {
    this.relatedPublication = relatedPublication;
  }
}
