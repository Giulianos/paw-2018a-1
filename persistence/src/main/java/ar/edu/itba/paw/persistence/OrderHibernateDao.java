package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.OrderDao;
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
		final Order order = new Order(publication, subscriber, quantity);
		
		em.persist(order);
		return order == null ? Optional.empty() : Optional.of(order);
	}

	@Override
	@Transactional
	public boolean confirm(Order order) {
		order.setConfirmed(true);
		return updateOrder(order);
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

	@Override
	@Transactional
	public boolean updateOrder(Order order) {
		try {
			em.refresh(order);
		} catch (Exception e) {
			System.out.println("----------------->EXCEPTION!!!!!!!");
			System.out.println(e);
			return false;
		}
		return true;
	}
	
}
