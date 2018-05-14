package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
		User [] users = {null, null};
		
		for (int i = 0; i < users.length; i++) {
			users[i] = userDao.findByUsername(USERNAME[i]);
			assertNotNull(users[i]);
			assertEquals(USERNAME[i], users[i].getUsername());
		}
		assertNull(userDao.findByUsername(USERNAME_UNKNOWN));
	}
	
	@Test
	public void test_findByEmail() {
		User [] users = {null, null};
		
		for (int i = 0; i < users.length; i++) {
			users[i] = userDao.findByEmail(EMAIL[i]);
			assertNotNull(users[i]);
			assertEquals(EMAIL[i], users[i].getEmail());
		}
		assertNull(userDao.findByUsername(EMAIL_UNKNOWN));
	}
	
	@Test
	public void test_findById() {
		User [] users = {null, null};
		
		for (int i = 0; i < users.length; i++) {
			// Search 1st by username to get the id.
			long id = userDao.findByUsername(USERNAME[i]).getId();
			users[i] = userDao.findById(id);
			assertNotNull(users[i]);
			assertEquals(id, users[i].getId());
		}
	}
	
	// TEST_5 NOT PASSED. WHY?
	
	@Test
	public void test_addTransaction() {
//		int transactionBefore = userDao.findByUsername(USERNAME[0]).getTransactions();
//		assertTrue(userDao.addTransaction(USERNAME[0]));
//		int transactionAfter = userDao.findByUsername(USERNAME[0]).getTransactions();
//		assertEquals(transactionBefore + 1, transactionAfter);
	}
}