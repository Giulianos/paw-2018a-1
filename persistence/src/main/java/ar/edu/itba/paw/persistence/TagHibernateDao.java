package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.TagDao;
import ar.edu.itba.paw.model.Tag;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class TagHibernateDao implements TagDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Tag createTag(String tag) {
    Tag newTag = new Tag(tag);

    em.persist(newTag);

    return newTag;
  }

  @Override
  @Transactional
  public List<Tag> list(String startingString, Integer maxQuantity) {
    TypedQuery<Tag> query = em.createQuery(
        "from Tag as t where t.tag like :tag",
        Tag.class
    );
    query.setParameter("tag", startingString + "%");
    query.setMaxResults(maxQuantity);

    List<Tag> results = query.getResultList();

    /** Initialize tag usage (size of publication proxys) */
    results.forEach(tag -> tag.getUsage());

    return results;
  }

  @Override
  public Optional<Tag> retrieve(String tag) {
    TypedQuery<Tag> query = em.createQuery("from Tag as t where t.tag = :tag", Tag.class);
    query.setParameter("tag", tag);

    List<Tag> tagEntity = query.getResultList();

    return tagEntity.isEmpty() ? Optional.empty() : Optional.of(tagEntity.get(0));
  }
}
