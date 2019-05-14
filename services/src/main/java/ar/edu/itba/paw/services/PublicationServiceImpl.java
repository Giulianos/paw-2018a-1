package ar.edu.itba.paw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class PublicationServiceImpl implements PublicationService {
	@Autowired
	private PublicationDao publicationDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private OrderDao orderDao;
	
	@Autowired
	private UserService us;
	
	@Override
	public Optional<Publication> findById(long id) {
		return publicationDao.findById(id);
	}

	@Override
	public List<Publication> findBySupervisor(String username) {
		User supervisor = userDao.findByUsername(username).get();
		return publicationDao.findBySupervisor(supervisor);
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
			results.removeIf((Publication pub)->pub.getRemainingQuantity() == 0);
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
	public Publication create(String supervisor, String description, float price, int quantity, final String image, final String tags) {
		User supervisorUser = userDao.findByUsername(supervisor).get();
		return publicationDao.create(supervisorUser,description,price,quantity,image, tags);
	}

	@Override
	@Transactional
	public boolean confirm(long id) {
		Publication publication = publicationDao.findById(id).get();
		if (publication.getRemainingQuantity() == 0) {
			return publicationDao.confirm(publication);
		}
		return false;
	}
	
	@Override
	@Transactional
	public boolean delete(long publication_id) {
		Publication publication = publicationDao.findById(publication_id).get();
		return publicationDao.delete(publication);
	}

	@Override
	@Transactional
	public boolean setNewSupervisor(String supervisor, long id) {
		Optional<User> supervisorUser = userDao.findByUsername(supervisor);
		Publication publication = publicationDao.findById(id).get();
		
		if(supervisorUser.isPresent()) {
			return publicationDao.setNewSupervisor(supervisorUser.get(), publication);	
		} else {
			return publicationDao.setNewSupervisor(null, publication);
		}
	}

	@Override
	public boolean hasSupervisor(long id) {
		Publication publication = publicationDao.findById(id).get();
		return publicationDao.hasSupervisor(publication);
	}

	@Override
	@Transactional
	public boolean check(long id) {
		Publication managedPublication = publicationDao.findById(id).get();
		User supervisor = managedPublication.getSupervisor();
		
		for(Order order : managedPublication.getOrders()) {
			if(order.getSupervisorReputation() != null) {
				us.updateReputation(supervisor.getUsername(), order.getSupervisorReputation());
			}
			if(order.getSubscriberReputation() != null) {
				us.updateReputation(order.getSubscriber().getUsername(), order.getSubscriberReputation());
			}
		}
		
		publicationDao.delete(managedPublication);
		return true;
	}

}