package ar.edu.itba.paw.webapp.dto;

import javax.validation.constraints.Pattern;

public class PublicationStateDTO {
  @Pattern(regexp = "PURCHASED")
  private String status;

  public PublicationStateDTO(String status) {
    this.status = status;
  }

  public PublicationStateDTO() {
    // Needed by JAX-RS
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
