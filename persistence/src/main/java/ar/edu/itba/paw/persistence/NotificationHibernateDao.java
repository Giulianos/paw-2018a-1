package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.model.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Primary
@Repository
public class NotificationHibernateDao implements NotificationDao {

 @PersistenceContext
 private EntityManager em;

  @Override
  public Notification create(User user, NotificationType type, Publication relatedPublication, Order relatedOrder, Message relatedMessage) {
    Notification newNotification = new Notification(type, user, relatedPublication, relatedOrder, relatedMessage);

    em.persist(newNotification);

    return newNotification;
  }

  @Override
  public List<Notification> getUnseen(Long userId) {
    final TypedQuery<Notification> query = em.createQuery("from Notification n where n.seen = false and user.id = :userid order by updatedAt DESC", Notification.class);

    query.setParameter("userid", userId);

    return query.getResultList();
  }

  @Override
  public List<Notification> getLatest(Long userId, Integer limit) {
    final TypedQuery<Notification> query = em.createQuery("from Notification n where user.id = :userid order by updatedAt DESC", Notification.class);

    query.setParameter("userid", userId);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public void update(Notification notification) {
    em.merge(notification);
    em.flush();
  }
}
