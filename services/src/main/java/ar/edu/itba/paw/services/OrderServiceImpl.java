package ar.edu.itba.paw.services;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Autowired
	private UserServiceImpl userService;

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
	@Transactional
	public Order create(Publication publication, User subscriber, int quantity) {

		Publication managedPublication = publicationDao.findById(publication.getId()).get();
		Integer currentRemainingQuantity = managedPublication.getRemainingQuantity();
		managedPublication.setRemainingQuantity(currentRemainingQuantity-quantity);
		publicationDao.updatePublication(managedPublication);
		
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
	@Transactional
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
	@Transactional
	public boolean delete(long publication_id, String subscriber) {
		Publication publication = publicationDao.findById(publication_id).get();
		User subscriberUser = userDao.findByUsername(subscriber).get();
		Order order = orderDao.findByPublicationAndSupervisor(publication, subscriberUser).get();
		if(order.getSubscriberReputation() != null) {
			userService.updateReputation(order.getSubscriber().getUsername(), order.getSubscriberReputation());
		}
		if(order.getSupervisorReputation() != null) {
			userService.updateReputation(order.getPublication().getSupervisor().getUsername(), order.getSupervisorReputation());
		}
		return orderDao.delete(order);
	}

	@Override
	public List<Order> findFinalizedBySubscriber(String subscriber) {
		User subscriberUser = userDao.findByUsername(subscriber).get();
		List<Order> orders = new ArrayList<>(); 
		orders.addAll(subscriberUser.getOrders());
		orders.removeIf((Order ord) -> !ord.getPublication().getConfirmed());
		
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

	@Override
	@Transactional
	public void setSupervisorReputation(Order order, Integer reputation) {
		order.setSupervisorReputation(reputation);
	}

	@Override
	@Transactional
	public void setSupervisorSubscriber(Order order, Integer reputation) {
		order.setSubscriberReputation(reputation);		
	}
}