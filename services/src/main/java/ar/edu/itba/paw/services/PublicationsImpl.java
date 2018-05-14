package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.ConfirmedOrders;
import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.interfaces.Publications;
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

	@Override
	public Publication findById(long id) {
		return publicationDao.findById(id);
	}

	@Override
	public List<Publication> findBySupervisor(String username) {
		return publicationDao.findBySupervisor(username);
	}

	@Override
	public List<Publication> findByDescription(String description) {
		return publicationDao.findByDescription(description);
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
		int remainingQuantity = findById(id).getQuantity();
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
}