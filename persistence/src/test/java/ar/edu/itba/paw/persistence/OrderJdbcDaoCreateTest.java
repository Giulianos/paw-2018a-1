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

import ar.edu.itba.paw.model.Order;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
public class OrderJdbcDaoCreateTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] USERNAME = {"user1","user2"};
	private static final String SUPERVISOR = "user1";
	private static final String DESCRIPTION = "desc";
	private static final int QUANTITY = 10;
	private static final int PRICE = 10;
	private static final int SUB_QUANTITY = 1;
	private static final String IMAGE = "";
	
	@Autowired
	private DataSource ds;
	@Autowired
	private UserJdbcDao userDao;
	@Autowired
	private PublicationJdbcDao publicationDao;
	@Autowired
	private OrderJdbcDao orderDao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "publications");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "orders");
		// Create users prior to publications in accordance to foreign key restrictions.
					for (int i = 0; i < USERNAME.length; i++) {
						userDao.create(USERNAME[i], EMAIL[i], PASSWORD);
					}
					// Create publication prior to orders
					publicationDao.create(SUPERVISOR, DESCRIPTION, PRICE, QUANTITY, IMAGE);
	}
	
	@Test
	public void test_create() {
		Order [] orders = {null, null};
		long publication_id = publicationDao.findBySupervisor(SUPERVISOR).get(0).getId();
		
		for (int i = 0; i < orders.length; i++) {
			orders[i] = orderDao.create(publication_id, USERNAME[i], SUB_QUANTITY);
			assertNotNull(orders[i]);
			assertEquals(USERNAME[i], orders[i].getSubscriber());
			assertEquals(publication_id, orders[i].getPublication_id());
		}
		assertEquals(orders.length, JdbcTestUtils.countRowsInTable(jdbcTemplate, "orders"));
	}
}