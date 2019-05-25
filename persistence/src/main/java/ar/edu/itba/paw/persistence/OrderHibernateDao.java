package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
@Repository
public class OrderHibernateDao implements OrderDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Optional<Order> find(Publication publication, User orderer) {

    final TypedQuery<Order> query = em.createQuery("from Order as o where (o.publication = :publication AND o.orderer = :orderer)", Order.class);
    query.setParameter("publication", publication);
    query.setParameter("orderer", orderer);

    try {
      return Optional.of(query.getSingleResult());
    } catch (NoResultException e) {
      return Optional.empty();
    }
  }

  @Override
  @Transactional
  public Order create(Publication publication, User orderer, Long quantity) {
    final Order newOrder = new Order(publication, orderer, quantity);
    publication.addOrder(newOrder);

    em.persist(newOrder);

    return newOrder;
  }

  @Override
  @Transactional
  public void update(final Order order) {
    em.merge(order);
    em.flush();
  }
}
