package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@Primary 
@Repository 
public class PublicationHibernateDao implements PublicationDao {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<Publication> findById(long id) {
		Publication publication = em.find(Publication.class, id);
		return  publication == null ? Optional.empty() : Optional.of(publication);
	}
	
	@Override
	public List<Publication> findBySupervisor(User supervisor) {
		final TypedQuery<Publication> query = em.createQuery("from Publication as p where p.supervisor = :supervisor", Publication.class);
		query.setParameter("supervisor", supervisor);
		return query.getResultList();
	}

	@Override
	public List<Publication> findByDescription(String description) {
		final TypedQuery<Publication> query = em.createQuery("from Publication as p where p.description = :description", Publication.class);
		query.setParameter("description", description);
		return query.getResultList();
	}

	@Override
	public List<Publication> findByDescription(String description, boolean checkSupervisor) {
		final TypedQuery<Publication> query;
		if(!checkSupervisor) {
			query = em.createQuery("from Publication as p where p.description = :description", Publication.class);
			query.setParameter("description", description);
			return query.getResultList();
		} else {
			query = em.createQuery("from Publication as p where (p.description = :description AND p.supervisor IS NOT NULL)", Publication.class);
			query.setParameter("description", description);
			return query.getResultList();
		}
	}

	@Override
	public List<Publication> findByPrice(float minPrice, float maxPrice) {
		final TypedQuery<Publication> query = em.createQuery("from Publication as p where (p.price >= :minPrice AND p.price <= :maxPrice)", Publication.class);
		query.setParameter("minPrice", minPrice);
		query.setParameter("maxPrice", maxPrice);
		return query.getResultList();
	}

	@Override
	public List<Publication> findByQuantity(int quantity) {
		final TypedQuery<Publication> query = em.createQuery("from Publication as p where p.quantity = :quantity", Publication.class);
		query.setParameter("quantity", quantity);
		return query.getResultList();
	}

	@Override
	public List<Publication> findByQuantity(int minQuantity, int maxQuantity) {
		final TypedQuery<Publication> query = em.createQuery("from Publication as p where (p.quantity >= :minQ AND p.quantity <= :maxQ)", Publication.class);
		query.setParameter("minQ", minQuantity);
		query.setParameter("maxQ", maxQuantity);
		return query.getResultList();
	}

	@Override
	@Transactional
	public Publication create(User supervisor, String description, float price, int quantity, String image) {
		final Publication publication = new Publication(supervisor, description, price, quantity, image);
		
		em.persist(publication);
		return publication;
	}

	@Override
	@Transactional
	public boolean confirm(Publication publication) {
		publication.setConfirmed(true);
		return updatePublication(publication);
	}

	@Override
	@Transactional
	public boolean delete(Publication publication) {
		try {
			em.remove(publication);	
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean setNewSupervisor(User supervisor, Publication publication) {
		publication.setSupervisor(supervisor);
		return updatePublication(publication);
	}

	@Override
	public boolean hasSupervisor(Publication publication) {
		return publication.getSupervisor() != null;
	}

	@Override
	@Transactional
	public boolean updatePublication(Publication publication) {
		try {
			em.refresh(publication);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
}
