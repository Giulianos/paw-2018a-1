package ar.edu.itba.paw.services;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.ConfirmedOrders;
import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.interfaces.Publications;
import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.ConfirmedOrder;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;

@Primary
@Service
public class PublicationsImpl implements Publications {
	@Autowired
	private PublicationDao publicationDao;
	@Autowired
	private Orders orders;
	@Autowired
	private ConfirmedOrders confirmedOrders;
	@Autowired
	private Users users;
	@Autowired
	private UserDao userDao;
	
	@Override
	public Optional<Publication> findById(long id) {
		return publicationDao.findById(id);
	}

	@Override
	public List<Publication> findBySupervisor(String username) {
		return publicationDao.findBySupervisor(username);
	}

	@Override
	public List<Publication> findBySupervisor(String username, int fromIndex) {
		List<Publication> results = findBySupervisor(username);
		int toIndex = results.size() - 1;
		
		if (fromIndex <= 0 || results.isEmpty()) {
			return results;
		}

		if (fromIndex > toIndex) {
			return results.subList(toIndex, toIndex); // empty list.
		}

		return results.subList(fromIndex, toIndex+1);
	}

	@Override
	public List<Publication> findByDescription(String description) {
		return publicationDao.findByDescription(description);
	}
	
	@Override
	public List<Publication> findByDescription(String description, boolean checkSupervisor) {
		return publicationDao.findByDescription(description, checkSupervisor);
	}
	
	@Override
	public List<Publication> findByDescription(String description, boolean checkSupervisor, boolean checkRemainingQuantity) {
		List<Publication> results = findByDescription(description, checkSupervisor);
		
		if (checkRemainingQuantity) {
			List<Publication> needToRemove = new LinkedList<>();
			
			for(Publication publication : results) {
				if(remainingQuantity(publication.getId()) == 0)
					needToRemove.add(publication);
				publication.setRemainingQuantity(remainingQuantity(publication.getId()));
			}
			results.removeAll(needToRemove);
		}
		return results;
	}

	@Override
	public List<Publication> findByDescription(String description, int fromIndex, boolean checkSupervisor,
			boolean checkRemainingQuantity) {
		List<Publication> results = findByDescription(description, checkSupervisor, checkRemainingQuantity);
		int toIndex = results.size() - 1;
		
		if (fromIndex <= 0 || results.isEmpty()) {
			return results;
		}

		if (fromIndex > toIndex) {
			return results.subList(toIndex, toIndex); // empty list.
		}

		return results.subList(fromIndex, toIndex+1);
	}

	@Override
	public List<Publication> findByPrice(float minPrice, float maxPrice) {
		return publicationDao.findByPrice(minPrice,maxPrice);
	}

	@Override
	public List<Publication> findByQuantity(int quantity) {
		return publicationDao.findByQuantity(quantity);
	}

	@Override
	public List<Publication> findByQuantity(int minQuantity, int maxQuantity) {
		return publicationDao.findByQuantity(minQuantity,maxQuantity);
	}

	@Override
	public Publication create(String supervisor, String description, float price, int quantity, final String image) {
		return publicationDao.create(supervisor,description,price,quantity,image);
	}

	@Override
	public int remainingQuantity(long id) {
		int remainingQuantity = findById(id).get().getQuantity();
		List<Order> currentOrders = orders.findByPublicationId(id);

		for (Order order : currentOrders) {
			remainingQuantity -= order.getQuantity();
		}
		return remainingQuantity;
	}

	@Override
	public boolean confirm(long id) {
		if (remainingQuantity(id) == 0) {
			return publicationDao.confirm(id);
		}
		return false;
	}

	@Override
	public boolean confirmOrders(long id) {
		if (orders.areConfirmed(id)) {
			List<Order> currentOrders = orders.findByPublicationId(id);
			orders.delete(id);
			
			for (Order order : currentOrders) {
				confirmedOrders.create(order.getPublication_id(),order.getSubscriber(),order.getQuantity());
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean confirmFulfillment(long id) {
		if (confirmedOrders.areFulfilled(id)) {
			List<ConfirmedOrder> currentOrders = confirmedOrders.findByPublicationId(id);
			confirmedOrders.delete(id);
			
			for (ConfirmedOrder order : currentOrders) {
				users.addTransaction(order.getBuyer());
			}
			publicationDao.delete(id);
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean delete(long publication_id) {
		return publicationDao.delete(publication_id);
	}

	@Override
	public boolean setNewSupervisor(String user, long id) {
		return publicationDao.setNewSupervisor(user, id);
	}

	@Override
	public boolean hasSupervisor(long id) {
		return publicationDao.hasSupervisor(id);
	}

	@Override
	public boolean loadPublicationSubscribers(Publication pub) {
		pub.setSubscribers(userDao.getSubscribersOfPublication(pub.getId()));
		return false;
	}
}