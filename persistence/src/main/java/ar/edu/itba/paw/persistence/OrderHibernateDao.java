package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
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
  public List<Order> userOrders(User user, Integer page, Integer pageSize) {
    final TypedQuery<Order> query = em.createQuery("from Order as o where (o.orderer = :orderer)", Order.class);
    query.setParameter("orderer", user);
    query.setFirstResult(pageSize*page);
    query.setMaxResults(pageSize);

    return query.getResultList();
  }

  @Override
  public Long userOrdersQuantity(User user) {
    final TypedQuery<Long> query = em.createQuery("select count(*) from Order as o where (o.orderer = :orderer)", Long.class);
    query.setParameter("orderer", user);

    return query.getSingleResult();
  }

  @Override
  @Transactional
  public void update(final Order order) {
    em.merge(order);
    em.flush();
  }
}
