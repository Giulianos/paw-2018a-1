package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@Primary 
@Repository 
public class OrderHibernateDao implements OrderDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Order> findBySubscriber(User subscriber) {
		return subscriber == null ? null : subscriber.getOrders();
	}

	@Override
	public List<Order> findByPublication(Publication publication) {
		return  publication == null ? null : publication.getOrders();
	}
	
	@Override
	public Optional<Order> findByPublicationAndSupervisor(Publication publication, User subscriber) {
		final TypedQuery<Order> query = em.createQuery("from Order as o where (o.publication = :publication AND o.subscriber = :subscriber)", Order.class);
		query.setParameter("publication", publication);
		query.setParameter("subscriber", subscriber);
		final List<Order> list = query.getResultList();
		return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)); 
		
	}

	@Override
	@Transactional
	public Optional<Order> create(Publication publication, User subscriber, int quantity) {
		Optional<Order> optOrd = findByPublicationAndSupervisor(publication, subscriber);
		Order order;
		if(optOrd.isPresent()) {
			order = optOrd.get();
			order.setQuantity(order.getQuantity() + quantity);
		}
		else {
			order = new Order(publication, subscriber, quantity);	
		}
		
		try {
			em.persist(order);
		} catch (Exception e) {
			return null;
		}
		return order == null ? Optional.empty() : Optional.of(order);
	}

	@Override
	public boolean delete(Order order) {
		Integer quantity = order.getQuantity();
		Integer currentRemainingQuantity = order.getPublication().getRemainingQuantity();
		order.getPublication().setRemainingQuantity(currentRemainingQuantity + quantity);
		try {
			em.remove(order);	
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean updateOrder(Order order) {
		em.flush();
		return true;
	}
	
}
