package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.webapp.utils.URLResolver;

public class OrderDTO {
  private Long publicationId;
  private Long ordererId;
  private String ordererUrl;
  private String publicationUrl;

  public OrderDTO(final Order order) {
    this.publicationId = order.getPublication().getId();
    this.ordererId = order.getOrderer().getId();
    this.ordererUrl = URLResolver.getFullURL("/users/" + this.ordererId);
    this.publicationUrl = URLResolver.getFullURL("/publications/" + this.publicationId);
  }

  public OrderDTO() {
    // Empty constructor needed by JAX-RS
  }

  public Long getPublicationId() {
    return publicationId;
  }

  public void setPublicationId(Long publicationId) {
    this.publicationId = publicationId;
  }

  public Long getOrdererId() {
    return ordererId;
  }

  public void setOrdererId(Long ordererId) {
    this.ordererId = ordererId;
  }

  public String getOrdererUrl() {
    return ordererUrl;
  }

  public void setOrdererUrl(String ordererUrl) {
    this.ordererUrl = ordererUrl;
  }

  public String getPublicationUrl() {
    return publicationUrl;
  }

  public void setPublicationUrl(String publicationUrl) {
    this.publicationUrl = publicationUrl;
  }
}
