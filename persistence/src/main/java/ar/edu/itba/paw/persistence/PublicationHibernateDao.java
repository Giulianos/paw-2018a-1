package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
@Repository
public class PublicationHibernateDao implements PublicationDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  @Transactional
  public Optional<Publication> findById(Long id) {
    Publication publication = em.find(Publication.class, id);

    if (publication != null) {
      publication.getImages().forEach(Image::getId);
      return Optional.of(publication);
    } else {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Publication create(User supervisor, String description, Double unitPrice, Long quantity, String detailedDescription) {
    final Publication newPublication = new Publication(supervisor, description, unitPrice, quantity, detailedDescription);
    em.persist(newPublication);

    return newPublication;
  }

  @Override
  @Transactional
  public void update(Publication publication) {
    em.merge(publication);
    em.flush();
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    Optional<Publication> pub = findById(id);

    if(pub.isPresent()) {
      em.remove(pub.get());
    }
  }
}
