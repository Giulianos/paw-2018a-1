package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "images")
public class Image extends TimestampedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Access(AccessType.PROPERTY)
  private Long id;

  @Column(name = "base64", nullable = false, unique = true)
  private String image;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @Access(AccessType.PROPERTY)
  @JoinColumn(name = "publication_id", updatable = false)
  private Publication publication;

  public Image(String image) {
    this.image = image;
  }

  public Image() {
    // Hibernate needs this
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public Publication getPublication() {
    return publication;
  }

  public void setPublication(Publication publication) {
    this.publication = publication;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Image image = (Image) o;
    return Objects.equals(id, image.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
