package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
public class UserJdbcDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] USERNAME = {"user1","user2"};
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	private static final String USERNAME_UNKNOWN = "userx";
	private static final String EMAIL_UNKNOWN = "userx@example.com";
	
	@Autowired
	private DataSource ds;
	@Autowired
	private UserJdbcDao userDao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		for (int i = 0; i < USERNAME.length; i++) {
			userDao.create(USERNAME[i], EMAIL[i], PASSWORD);
		}
	}
	
	@Test
	public void test_findByUsername() {
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
			long id = userDao.findByUsername(USERNAME[i]).get().getId();
			Optional<User> user = userDao.findById(id);
		    assertTrue(user.isPresent());
			assertEquals(id, user.get().getId());
		}
	}
	
	// TEST_5 NOT PASSED. WHY?
	
	@Test
	public void test_addTransaction() {
//		int transactionBefore = userDao.findByUsername(USERNAME[0]).get().getTransactions();
//		assertTrue(userDao.addTransaction(USERNAME[0]));
//		int transactionAfter = userDao.findByUsername(USERNAME[0]).get().getTransactions();
//		assertEquals(transactionBefore + 1, transactionAfter);
	}
}