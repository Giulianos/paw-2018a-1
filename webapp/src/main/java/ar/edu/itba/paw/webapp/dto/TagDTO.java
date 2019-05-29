package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Tag;

public class TagDTO {
  private String tag;
  private Integer usage;

  public TagDTO() {
    // Empty constructor needed by JAX-RS
  }

  public TagDTO(final Tag tag) {
    this.tag = tag.getTag();
    this.usage = tag.getUsage();
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public Integer getUsage() {
    return usage;
  }

  public void setUsage(Integer usage) {
    this.usage = usage;
  }
}
