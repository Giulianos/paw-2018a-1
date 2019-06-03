package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.MessageDao;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Primary
@Repository
public class MessageHibernateDao implements MessageDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public Message createMessage(Order order, User from, User to, String message) {
    Message newMessage = new Message(order, message, false, from, to);

    order.addMessage(newMessage);
    em.persist(newMessage);

    return newMessage;
  }

  @Override
  @Transactional
  public void markRead(List<Message> messages) {
    messages.forEach(m -> m.setReadByReceiver(true));
    messages.forEach(m -> em.merge(m));
    em.flush();
  }
}
