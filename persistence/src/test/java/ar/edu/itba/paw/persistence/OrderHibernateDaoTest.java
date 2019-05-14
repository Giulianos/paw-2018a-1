package ar.edu.itba.paw.persistence;

import static org.junit.Assert.*;

import java.util.List;
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

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
public class OrderHibernateDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] USERNAME = {"user1","user2"};
	private static final String DESCRIPTION = "desc";
	private static final String TAG = "tag";
	private static final int QUANTITY = 10;
	private static final int PRICE = 10;
	private static final int SUB_QUANTITY = 1;
	private static final String IMAGE = "";
	private static final String USERNAME_UNKNOWN = "userx";
	private static final String EMAIL_UNKNOWN = "userx@example.com";

	@PersistenceContext
	private EntityManager em;
	
    @Autowired
    private OrderDao orderDao;

	private User [] users;
	private User supervisor;
	private User unknownUser;
	private Publication publication;
	private Order [] orders;
	
	@Before
	public void setUp() {
		users = new User[USERNAME.length];
		// Create users prior to publications in accordance to foreign key restrictions.
		for (int i = 0; i < USERNAME.length; i++) {
			users[i] = new User(USERNAME[i], EMAIL[i], PASSWORD);
			em.persist(users[i]);
		}
		unknownUser = new User(USERNAME_UNKNOWN, EMAIL_UNKNOWN, PASSWORD);
		em.persist(unknownUser);
		
		supervisor = users[0];
		// Create publication prior to orders
		publication = new Publication(supervisor, DESCRIPTION, PRICE, QUANTITY, IMAGE, TAG);
		em.persist(publication);

		orders = new Order[USERNAME.length];
		
		for (int i = 0; i < USERNAME.length; i++) {
			orders[i] = new Order(publication, users[i], SUB_QUANTITY);
			em.persist(orders[i]);
		}
	}
	
	@Test
	public void test_findBySubscriber() {
		List<Order> orders;
		
		for (int i = 0; i < USERNAME.length; i++) {
			orders = orderDao.findBySubscriber(users[i]);
			assertFalse(orders.isEmpty());
			
			for (int j = 0; j < orders.size(); j++) {
				assertEquals(users[i], orders.get(j).getSubscriber());
			}
		}
	}
	
	@Test
	public void test_findByPublication() {
		List<Order> orders = orderDao.findByPublication(publication);
		assertFalse(orders.isEmpty());
		
		for (int i = 0; i < USERNAME.length; i++) {
			assertEquals(users[i], orders.get(i).getSubscriber());
		}
		assertEquals(supervisor, orders.get(0).getPublication().getSupervisor());
	}
	
	@Test
	public void test_findByPublicationAndSubscriber() {
		Optional<Order> order = orderDao.findByPublicationAndSupervisor(publication, users[0]);
		assertTrue(order.isPresent());
		
		order = orderDao.findByPublicationAndSupervisor(publication, unknownUser);
		assertFalse(order.isPresent());
	}
	
	@Test
	public void test_delete() {
		List<Order> orders = orderDao.findBySubscriber(users[0]);
		assertFalse(orders.isEmpty());
		Order order = orders.get(0);
		Publication publication = order.getPublication();
		int publicationOldQuantity = publication.getRemainingQuantity();
		int userQuantity = order.getQuantity();

		assertTrue(orderDao.delete(order));

		assertEquals(publicationOldQuantity+userQuantity, publication.getRemainingQuantity());
	}
}