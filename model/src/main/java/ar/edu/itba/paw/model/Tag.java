package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tag extends TimestampedEntity{

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Access(AccessType.PROPERTY)
  private Long id;

  @Column(name = "tag", nullable = false, unique = true)
  private String tag;

  public Tag() {
    // hibernate needs this
  }

  public Tag(String tag) {
    this.tag = tag;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }
}
