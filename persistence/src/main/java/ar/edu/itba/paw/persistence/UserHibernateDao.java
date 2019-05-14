package ar.edu.itba.paw.persistence;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;

@Primary 
@Repository 
public class UserHibernateDao implements UserDao{

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public Optional<User> findById(long id) {
		User user = em.find(User.class, id);
		return  user == null ? Optional.empty() : Optional.of(user);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.username = :username", User.class);
		query.setParameter("username", username);
		final List<User> list = query.getResultList();
		if(list.isEmpty())
			System.out.println("The username wasnt found!");
		return  list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)); 
	}

	@Override
	public Optional<User> findByEmail(String email) {
		final TypedQuery<User> query = em.createQuery("from User as u where u.email = :email", User.class);
		query.setParameter("email", email);
		final List<User> list = query.getResultList();
		if(list.isEmpty())
			System.out.println("The email wasnt found!");
		return  list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)); 
	}

	@Override
	@Transactional
	public User create(String username, String email, String password) {
		final User user = new User(username, email, password);
		
		em.persist(user);
		return user;
	}

	@Override
	public boolean updateUser(User user) {
		em.flush();
		return true;
	}

}
