package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.webapp.utils.URLResolver;

public class OrderDTO {
  private UserDTO orderer;
  private PublicationDTO publication;
  private Long quantity;


  public OrderDTO(final Order order) {
    this.orderer = new UserDTO(order.getOrderer());
    this.publication = new PublicationDTO(order.getPublication());
    this.quantity = order.getQuantity();
  }

  public OrderDTO() {
    // Empty constructor needed by JAX-RS
  }

  public UserDTO getOrderer() {
    return orderer;
  }

  public void setOrderer(UserDTO orderer) {
    this.orderer = orderer;
  }

  public PublicationDTO getPublication() {
    return publication;
  }

  public void setPublication(PublicationDTO publication) {
    this.publication = publication;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
}
