package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Publication;

import java.util.List;
import java.util.stream.Collectors;

public class PublicationsDTO {

  private List<PublicationDTO> publications;

  public PublicationsDTO(List<Publication> publications) {
    this.publications = publications.stream().map(PublicationDTO::new).collect(Collectors.toList());
  }

  public PublicationsDTO() {
    // Needed by JAX-RS
  }

  public List<PublicationDTO> getPublications() {
    return publications;
  }

  public void setPublications(List<PublicationDTO> publications) {
    this.publications = publications;
  }
}
