package ar.edu.itba.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


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
	public Order create(Publication publication, User subscriber, int quantity) {
		final Order order = new Order(publication, subscriber, quantity);
		
		em.persist(order);
		return order;
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
	public boolean delete(Publication publication) {
		List<Order> orders = publication.getOrders();
		for(Order o : orders) {
			if(!delete(o))
				return false;
		}
		return true;
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
