package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.Order;

@Primary
@Service
public class OrdersImpl implements Orders {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private PublicationDao publicationDao;

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
	public int quantity(long publication_id) {
		int remainingQuantity = publicationDao.findById(publication_id).getQuantity();
		List<Order> orders = findByPublicationId(publication_id);

		for (Order order : orders) {
			remainingQuantity -= order.getQuantity();
		}

		return remainingQuantity;
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
}