package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Notification;
import org.springframework.security.access.method.P;

import java.util.Date;

public class NotificationDTO {
  private String type;
  private OrderDTO relatedOrder;
  private PublicationDTO relatedPublication;
  private MessageDTO relatedMessage;
  private Date date;

  public NotificationDTO() {
    // Empty constructor needed by JAX-RS
  }

  public NotificationDTO(final Notification notification) {
    this.type = notification.getType().toString();
    this.date = notification.getCreatedAt();

    if(notification.getRelatedOrder() != null) {
      this.relatedOrder = new OrderDTO(notification.getRelatedOrder());
    }

    if(notification.getRelatedPublication() != null) {
      this.relatedPublication = new PublicationDTO(notification.getRelatedPublication());
    }

    if(notification.getRelatedMessage() != null) {
      this.relatedMessage = new MessageDTO(notification.getRelatedMessage());
    }
  }

  public String getType() {
    return type;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
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

  public MessageDTO getRelatedMessage() {
    return relatedMessage;
  }

  public void setRelatedMessage(MessageDTO relatedMessage) {
    this.relatedMessage = relatedMessage;
  }

  public void setRelatedPublication(PublicationDTO relatedPublication) {
    this.relatedPublication = relatedPublication;
  }
}
