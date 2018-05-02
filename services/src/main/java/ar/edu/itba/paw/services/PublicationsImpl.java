package ar.edu.itba.paw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.interfaces.Publications;
import ar.edu.itba.paw.model.Publication;

@Primary
@Service
public class PublicationsImpl implements Publications {
	@Autowired
	private PublicationDao publicationDao;

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
	public Publication create(String supervisor, String description, float price, int quantity) {
		return publicationDao.create(supervisor,description,price,quantity);
	}
}