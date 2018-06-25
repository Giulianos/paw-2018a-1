package ar.edu.itba.paw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PublicationDao publicationDao;

	@Override
	public List<Order> findBySubscriber(String username) {
		User user = userDao.findByUsername(username).get();
		return orderDao.findBySubscriber(user);
	}

	@Override
	public List<Order> findBySubscriber(String username, boolean withFinalized) {
		List<Order> subscriptions = findBySubscriber(username);
		
		if (!withFinalized) {
			subscriptions.removeAll(findFinalizedBySubscriber(username));
		}
		return subscriptions;
	}

	@Override
	public List<Order> findByPublicationId(long publication_id) {
		Publication publication = publicationDao.findById(publication_id).get();
		return orderDao.findByPublication(publication);
	}

	@Override
	public Order create(Publication publication, User subscriber, int quantity) {
		Integer currentRemainingQuantity = publication.getRemainingQuantity();
		
		publication.setRemainingQuantity(currentRemainingQuantity - quantity);
		publicationDao.updatePublication(publication);
		return orderDao.create(publication, subscriber,quantity).get();
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
	public boolean delete(final long publication_id) {
		Publication publication = publicationDao.findById(publication_id).get();
		List<Order> orders = publication.getOrders();
		for(Order o : orders) {
			if(!orderDao.delete(o))
				return false;
		}
		return true;
	}
	
	@Override
	public boolean delete(long publication_id, String subscriber) {
		Publication publication = publicationDao.findById(publication_id).get();
		User subscriberUser = userDao.findByUsername(subscriber).get();
		Order order = orderDao.findByPublicationAndSupervisor(publication, subscriberUser).get();
		return orderDao.delete(order);
	}

	@Override
	public List<Order> findFinalizedBySubscriber(String subscriber) {
		User subscriberUser = userDao.findByUsername(subscriber).get();
		List<Order> orders = new ArrayList<>(); 
		orders.addAll(subscriberUser.getOrders());
		for(Order o: orders) {
			if(!orderDao.isConfirm(o))
				orders.remove(o);
		}
		return orders;
	}

	@Override
	public boolean anyHasNoSupervisor(List<Order> orders) {
		for (Order order : orders) {
			if(order.getPublication().getSupervisor() == null)
				return true;
		}
		return false;
	}
}