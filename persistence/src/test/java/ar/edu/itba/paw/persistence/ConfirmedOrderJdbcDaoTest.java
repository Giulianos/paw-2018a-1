package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.model.ConfirmedOrder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ConfirmedOrderJdbcDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] USERNAME = {"user1","user2"};
	private static final String SUPERVISOR = "user1";
	private static final String DESCRIPTION = "desc";
	private static final int QUANTITY = 10;
	private static final int PRICE = 10;
	private static final int SUB_QUANTITY = 1;
	private static final String IMAGE = "";
	
	private static int test = 0;
	
	@Autowired
	private DataSource ds;
	@Autowired
	private UserJdbcDao userDao;
	@Autowired
	private PublicationJdbcDao publicationDao;
	@Autowired
	private ConfirmedOrderJdbcDao confirmedOrderDao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		test++;
		if (test == 1) { // Test 1 - Create
			
			// Create users prior to publications in accordance to foreign key restrictions.
			for (int i = 0; i < USERNAME.length; i++) {
				userDao.create(USERNAME[i], EMAIL[i], PASSWORD);
			}
			// Create publication prior to orders
			publicationDao.create(SUPERVISOR, DESCRIPTION, PRICE, QUANTITY, IMAGE);
			
		} else {
			// For test 2 or higher I reset what I did in the create test
			// and create the table entries that I will use in the remaining tests.
			JdbcTestUtils.deleteFromTables(jdbcTemplate, "confirmedOrders");
			long publication_id = publicationDao.findBySupervisor(SUPERVISOR).get(0).getId();
			
			for (int i = 0; i < USERNAME.length; i++) {
				confirmedOrderDao.create(publication_id, USERNAME[i], SUB_QUANTITY);
			}
		}
	}
	
	@Test
	public void test1_create() {
		ConfirmedOrder [] orders = {null, null};
		long publication_id = publicationDao.findBySupervisor(SUPERVISOR).get(0).getId();
		
		for (int i = 0; i < orders.length; i++) {
			orders[i] = confirmedOrderDao.create(publication_id, USERNAME[i], SUB_QUANTITY);
			assertNotNull(orders[i]);
			assertEquals(USERNAME[i], orders[i].getBuyer());
			assertEquals(publication_id, orders[i].getPublication_id());
		}
		assertEquals(orders.length, JdbcTestUtils.countRowsInTable(jdbcTemplate, "confirmedOrders"));
	}
	
	@Test
	public void test2_findByBuyer() {
		List<ConfirmedOrder> orders;
		
		for (int i = 0; i < USERNAME.length; i++) {
			orders = confirmedOrderDao.findByBuyer(USERNAME[i]);
			assertFalse(orders.isEmpty());
			
			for (int j = 0; j < orders.size(); j++) {
				assertEquals(USERNAME[i], orders.get(j).getBuyer());
			}
		}
	}
	
	@Test
	public void test3_findById() {
		List<ConfirmedOrder> orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		assertFalse(orders.isEmpty());
		long id = orders.get(0).getPublication_id();
		
		orders = confirmedOrderDao.findByPublicationId(id);
		assertFalse(orders.isEmpty());
		
		assertEquals(id, orders.get(0).getPublication_id());
	}
	
	@Test
	public void test4_confirmPayment() {
		List<ConfirmedOrder> orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		assertFalse(orders.isEmpty());
		long id = orders.get(0).getPublication_id();
		
		assertFalse(orders.get(0).getPaid());
		confirmedOrderDao.confirmPayment(id, USERNAME[0]);
		
		orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		
		for (ConfirmedOrder order : orders) {
			if (id == order.getPublication_id()) {
				assertTrue(order.getPaid());
			}
		}
	}
	
	@Test
	public void test5_confirmDelivery() {
		List<ConfirmedOrder> orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		assertFalse(orders.isEmpty());
		long id = orders.get(0).getPublication_id();
		
		assertFalse(orders.get(0).getReceived());
		confirmedOrderDao.confirmDelivery(id, USERNAME[0]);
		
		orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		
		for (ConfirmedOrder order : orders) {
			if (id == order.getPublication_id()) {
				assertTrue(order.getReceived());
			}
		}
	}
	
	@Test
	public void test6_delete() {
		List<ConfirmedOrder> orders = confirmedOrderDao.findByBuyer(USERNAME[0]);
		assertFalse(orders.isEmpty());
		long id = orders.get(0).getPublication_id();
		
		assertFalse(confirmedOrderDao.findByPublicationId(id).isEmpty());
		confirmedOrderDao.delete(id);
		assertTrue(confirmedOrderDao.findByPublicationId(id).isEmpty());
	}
}