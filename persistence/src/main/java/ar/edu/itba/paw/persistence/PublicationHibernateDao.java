package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.model.Image;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;
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

    pub.ifPresent(p ->em.remove(p));
  }

  @Override
  public List<Publication> userPublications(String email) {
    final TypedQuery<Publication> query = em.createQuery("from Publication p where p.supervisor.email = :email and p.state != 4", Publication.class);
    query.setParameter("email", email);

    return query.getResultList();
  }

  @Override
  public List<Publication> latestPublications(Integer quantity) {
    final TypedQuery<Publication> query = em.createQuery("from Publication p where p.state = 0 order by p.createdAt desc", Publication.class);
    query.setMaxResults(quantity);

    return query.getResultList();
  }

  @Override
  public List<Publication> searchByTags(List<String> tags, Integer page, Integer pageSize) {
    Session sess = (Session)em.getDelegate();

    SQLQuery query = sess.createSQLQuery("SELECT p.*" +
        "    FROM publications p" +
        "    INNER JOIN" +
        "    (" +
        "       SELECT jp.publication_id, COUNT(DISTINCT jp.tag_id) as relevance" +
        "       FROM publication_tags as jp" +
        "       JOIN tags as t ON jp.tag_id = t.id" +
        "       WHERE t.tag IN (:tags)" +
        "       GROUP BY jp.publication_id" +
        "       HAVING COUNT(DISTINCT jp.tag_id) > 0" +
        "    ) t ON p.id = t.publication_id" +
        "    WHERE p.state = 0" +
        "    ORDER BY relevance desc");
    query.setParameterList("tags", tags);
    query.setMaxResults(pageSize);
    query.setFirstResult(page*pageSize);
    query.addEntity(Publication.class);

    return (List<Publication>)query.list();
  }

  @Override
  public Integer searchByTagsResultSize(List<String> tags) {
    Session sess = (Session)em.getDelegate();

    SQLQuery query = sess.createSQLQuery("SELECT count(*)" +
        "    FROM publications p" +
        "    INNER JOIN" +
        "    (" +
        "       SELECT jp.publication_id" +
        "       FROM publication_tags as jp" +
        "       JOIN tags as t ON jp.tag_id = t.id" +
        "       WHERE t.tag IN (:tags)" +
        "       GROUP BY jp.publication_id" +
        "       HAVING COUNT(DISTINCT jp.tag_id) > 0" +
        "    ) t ON p.id = t.publication_id" +
        "    WHERE p.state = 0");
    query.setParameterList("tags", tags);

    return ((BigInteger)query.uniqueResult()).intValue();
  }
}
