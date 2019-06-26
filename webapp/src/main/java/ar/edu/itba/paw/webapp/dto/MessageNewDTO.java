package ar.edu.itba.paw.webapp.dto;

import org.springframework.security.access.method.P;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MessageNewDTO {

  @NotNull
  @Size(min = 1)
  private String message;

  public MessageNewDTO(String message) {
    this.message = message;
  }

  public MessageNewDTO() {
    // Empty constructor needed by JAX-RS
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
