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

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class UserHibernateDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] NAME = {"alice","bob"};
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	private static final String EMAIL_UNKNOWN = "userx@example.com";

	@PersistenceContext
	private EntityManager em;
	
    @Autowired
    private UserDao userDao;

	@Before
	public void setUp() {
		for (int i = 0; i < NAME.length; i++) {
			em.persist(new User(NAME[i], EMAIL[i], PASSWORD));
		}
	}
	
	@Test
	public void test_findByEmail() {
		for (int i = 0; i < EMAIL.length; i++) {
			Optional<User> user = userDao.findByEmail(EMAIL[i]);
		    assertTrue(user.isPresent());
			assertEquals(EMAIL[i], user.get().getEmail());
		}
		assertFalse(userDao.findByEmail(EMAIL_UNKNOWN).isPresent());
	}
	
	@Test
	public void test_findById() {
		for (int i = 0; i < EMAIL.length; i++) {
			// Search 1st by email to get the id.
			Long id = userDao.findByEmail(EMAIL[i]).get().getId();
			Optional<User> user = userDao.findById(id);
		    assertTrue(user.isPresent());
			assertEquals(id, user.get().getId());
		}
	}
}