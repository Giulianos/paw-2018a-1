package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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

  @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
  private Set<Publication> publications = new HashSet<>();

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

  public Set<Publication> getPublications() {
    return publications;
  }

  public void addPublication(final Publication publication) {
    this.publications.add(publication);
  }

  public void removePublication(final Publication publication) {
    this.publications.remove(publication);
  }

  public Integer getUsage() {
    return this.getPublications().size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Tag tag1 = (Tag) o;
    return tag.equals(tag1.tag);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tag);
  }
}
