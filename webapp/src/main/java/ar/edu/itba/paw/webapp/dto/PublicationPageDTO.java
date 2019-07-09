package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.interfaces.service.Page;
import ar.edu.itba.paw.model.Publication;

import java.util.List;
import java.util.stream.Collectors;

public class PublicationPageDTO {
  private List<PublicationDTO> publications;
  private Integer nextPage;
  private Integer totalPages;

  public PublicationPageDTO(Page<Publication> resultPage) {
    this.publications = resultPage.getResult().stream().map(PublicationDTO::new).collect(Collectors.toList());
    this.totalPages = (resultPage.getTotalResultSize() + resultPage.getPageSize() - 1) / resultPage.getPageSize();
    this.nextPage = resultPage.getCurrentPage() + 1;
    if(this.nextPage >= this.totalPages) {
      this.nextPage = null;
    }
  }

  public PublicationPageDTO() {
    // Empty constructor needed by JAX-RS
  }

  public List<PublicationDTO> getPublications() {
    return publications;
  }

  public void setPublications(List<PublicationDTO> publications) {
    this.publications = publications;
  }

  public Integer getNextPage() {
    return nextPage;
  }

  public void setNextPage(Integer nextPage) {
    this.nextPage = nextPage;
  }

  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }
}
