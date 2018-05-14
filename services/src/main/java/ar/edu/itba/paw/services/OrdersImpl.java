package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.model.Order;

@Primary
@Service
public class OrdersImpl implements Orders {
	@Autowired
	private OrderDao orderDao;

	@Override
	public List<Order> findBySubscriber(String username) {
		return orderDao.findBySubscriber(username);
	}

	@Override
	public List<Order> findByPublicationId(long publication_id) {
		return orderDao.findByPublicationId(publication_id);
	}

	@Override
	public Order create(long publication_id, String subscriber, int quantity) {
		return orderDao.create(publication_id,subscriber,quantity);
	}

	@Override
	public boolean confirm(long publication_id, String subscriber) {
		return orderDao.confirm(publication_id,subscriber);
	}

	@Override
	public boolean areConfirmed(long publication_id) {
		List<Order> orders = findByPublicationId(publication_id);

		for (Order order : orders) {
			if (!order.getConfirmed()) {
				return false;
			}
		}		
		return true;
	}

	@Override
	public boolean delete(long publication_id) {
		return orderDao.delete(publication_id);
	}
	
	@Override
	public boolean delete(long publication_id, String subscriptor) {
		return orderDao.delete(publication_id, subscriptor);
	}

	@Override
	public List<Order> findFinalizedBySubscriber(String username) {
		return orderDao.findFinalizedBySubscriber(username);
	}
}