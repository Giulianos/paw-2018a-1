package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Tag;

import java.util.List;
import java.util.stream.Collectors;

public class TagListDTO {
  private List<TagDTO> tags;

  public TagListDTO() {
    // Empty constructor needed by JAX-RS
  }

  public TagListDTO(final List<Tag> tags) {
    this.tags = tags.parallelStream().map(TagDTO::new).collect(Collectors.toList());
  }

  public List<TagDTO> getTags() {
    return tags;
  }

  public void setTags(List<TagDTO> tags) {
    this.tags = tags;
  }
}
