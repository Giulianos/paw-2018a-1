package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.model.Review;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class ReviewHibernateDao implements ReviewDao {

  @PersistenceContext
  EntityManager em;

  @Override
  public List<Review> userReviews(Long userId) {
    TypedQuery<Review> query = em.createQuery("from Review as r where r.order.publication.supervisor.id = :userId", Review.class);
    query.setParameter("userId", userId);
    return query.getResultList();
  }
}
