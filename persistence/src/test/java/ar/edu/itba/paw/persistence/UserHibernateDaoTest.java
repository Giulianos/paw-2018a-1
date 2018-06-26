package ar.edu.itba.paw.persistence;

import static org.junit.Assert.*;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class UserHibernateDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] USERNAME = {"user1","user2"};
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	private static final String USERNAME_UNKNOWN = "userx";
	private static final String EMAIL_UNKNOWN = "userx@example.com";

	@PersistenceContext
	private EntityManager em;
	
    @Autowired
    private UserDao userDao;

	@Before
	public void setUp() {
		for (int i = 0; i < USERNAME.length; i++) {
			em.persist(new User(USERNAME[i], EMAIL[i], PASSWORD));
		}
	}
	
	@Test
	public void test_findByUsername() {
		userDao.findByUsername(USERNAME_UNKNOWN);
		for (int i = 0; i < USERNAME.length; i++) {
		    Optional<User> user = userDao.findByUsername(USERNAME[i]);
		    assertTrue(user.isPresent());
			assertEquals(USERNAME[i], user.get().getUsername());
		}
		assertFalse(userDao.findByUsername(USERNAME_UNKNOWN).isPresent());
	}
	
	@Test
	public void test_findByEmail() {
		for (int i = 0; i < EMAIL.length; i++) {
			Optional<User> user = userDao.findByEmail(EMAIL[i]);
		    assertTrue(user.isPresent());
			assertEquals(EMAIL[i], user.get().getEmail());
		}
		assertFalse(userDao.findByUsername(EMAIL_UNKNOWN).isPresent());
	}
	
	@Test
	public void test_findById() {
		for (int i = 0; i <USERNAME.length; i++) {
			// Search 1st by username to get the id.
			Long id = userDao.findByUsername(USERNAME[i]).get().getId();
			Optional<User> user = userDao.findById(id);
		    assertTrue(user.isPresent());
			assertEquals(id, user.get().getId());
		}
	}
}