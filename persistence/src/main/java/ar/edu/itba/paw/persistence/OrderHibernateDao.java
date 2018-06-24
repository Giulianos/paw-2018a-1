package ar.edu.itba.paw.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.model.Order;

public class OrderHibernateDao implements OrderDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<Order> findBySubscriber(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Order> findByPublicationId(long publication_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Order create(long publication_id, String subscriber, int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirm(long publication_id, String subscriber) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long publication_id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long publication_id, String subscriber) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
