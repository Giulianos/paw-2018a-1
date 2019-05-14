package ar.edu.itba.paw.webapp.dto;

public class SampleEchoDTO {
  private String message;

  public SampleEchoDTO(String message) {
    this.message = message;
  }

  public SampleEchoDTO() {
    // Empty constructor needed by JAX-RS
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
