package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public class PublicationHibernateDao implements PublicationDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Publication> findById(long id) {
		Publication publication = em.find(Publication.class, id);
		return  publication == null ? Optional.empty() : Optional.of(publication);
	}

	@Override
	public List<Publication> findBySupervisor(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> findByDescription(String description) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> findByDescription(String description, boolean checkSupervisor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> findByPrice(float minPrice, float maxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> findByQuantity(int quantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Publication> findByQuantity(int minQuantity, int maxQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Publication create(String supervisor, String description, float price, int quantity, String image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirm(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean setNewSupervisor(String user, long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean hasSupervisor(long id) {
		// TODO Auto-generated method stub
		return false;
	}

}
