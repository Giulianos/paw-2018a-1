package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OrderNewDTO {

  @NotNull
  @Min(1)
  private Long quantity;

  public OrderNewDTO() {
    //  Empty constructor needed by JAX-RS
  }

  public OrderNewDTO(Long quantity) {
    this.quantity = quantity;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }
}
