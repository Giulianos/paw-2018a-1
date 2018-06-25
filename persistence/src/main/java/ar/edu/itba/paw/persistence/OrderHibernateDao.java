package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertNotNull;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

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
	public Optional<Order> create(Publication publication, User subscriber, int quantity) {
		final Order order = new Order(publication, subscriber, quantity);
		
		em.persist(order);
		return order == null ? Optional.empty() : Optional.of(order);
	}

	@Override
	public boolean confirm(Order order) {
		order.setConfirmed(true);
		try {
			em.refresh(order);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isConfirm(Order order) {
		return order.getConfirmed();
	}

	@Override
	public boolean delete(Order order) {
		try {
			em.remove(order);	
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
