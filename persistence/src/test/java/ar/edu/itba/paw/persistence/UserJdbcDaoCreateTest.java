package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class UserJdbcDaoCreateTest {
	private static final String PASSWORD = "pass";
	private static final String [] USERNAME = {"user1","user2"};
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};

	@Autowired
	private DataSource ds;
	@Autowired
	private UserJdbcDao userDao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
	}
	
	@Test
	public void test_create() {
		User [] users = {null, null};
		
		for (int i = 0; i < users.length; i++) {
			users[i] = userDao.create(USERNAME[i], EMAIL[i], PASSWORD);
			assertNotNull(users[i]);
			assertEquals(USERNAME[i], users[i].getUsername());
			assertEquals(EMAIL[i], users[i].getEmail());
		}
		assertEquals(users.length, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));
	}
}