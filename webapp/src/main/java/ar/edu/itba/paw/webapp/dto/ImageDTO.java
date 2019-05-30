package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Image;

public class ImageDTO {

  private String base64;
  private Long id;

  public ImageDTO(final Image image) {
    this.id = image.getId();
    this.base64 = image.getImage();
  }

  public ImageDTO() {
    // Empty constructor needed by JAX-RS
  }

  public String getBase64() {
    return base64;
  }

  public void setBase64(String base64) {
    this.base64 = base64;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
