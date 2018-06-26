package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.MessageDao;
import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.User;

@Primary 
@Repository 
public class MessageHibernateDao implements MessageDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Message> create(User from, User to, Order order, String text) {
		Message createdMessage = new Message(order, from, to, text, false);
		em.persist(createdMessage);
		return Optional.of(createdMessage);
	}

	@Override
	public List<Message> retreiveFromOrder(Order order) {
		final TypedQuery<Message> query = em.createQuery("from Message as m where m.order = :order", Message.class);
		query.setParameter("order", order);
		final List<Message> list = query.getResultList();
		return list;
	}

}
