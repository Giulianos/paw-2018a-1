package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.*;
import ar.edu.itba.paw.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = { TestConfig.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class MessageHibernateDaoTest {

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PublicationDao publicationDao;

	@Autowired
	private OrderDao orderDao;

	private User testSupervisor;
	private User testOrderer;

	private Publication testPublication;
	private Order testOrder;

	@Before
	public void testSetup() {
		this.testSupervisor = userDao.create("John Doe", "john.doe@example.com", "password123");
		this.testOrderer = userDao.create("Jane Doe", "jane.doe@example.com", "password123");

		this.testPublication = publicationDao.create(testSupervisor, "Test publication", 1.0, 2L, "");
		this.testOrder = orderDao.create(testPublication, testOrderer, 1L);
	}

	@Test
	public void createMessageThenRetrieveAndDelete() {
		// Create new message in order
		Message testMessage = messageDao.createMessage(testOrder, testOrderer, testSupervisor, "Hi supervisor!");
		orderDao.update(testOrder);

		// Retrieve the order
		Optional<Order> order2 = orderDao.find(testPublication, testOrderer);

		assertTrue(order2.isPresent());
		assertEquals(order2.get().getMessages().size(), 1);

		// Remove message
		order2.get().removeMessage(testMessage);
		orderDao.update(order2.get());

		Optional<Order> order3 = orderDao.find(testPublication, testOrderer);

		assertTrue(order3.isPresent());
		assertEquals(order3.get().getMessages().size(), 0);
	}

	@Test
	public void testReadMark() {
		// Create new message in order
		Message testMessage = messageDao.createMessage(testOrder, testOrderer, testSupervisor, "Hi supervisor!");
		orderDao.update(testOrder);

		// Retrieve the order
		Optional<Order> order2 = orderDao.find(testPublication, testOrderer);

		// Check if all are unread
		assertTrue(order2.isPresent());
		order2.get().getMessages().forEach(m -> assertFalse(m.getReadByReceiver()));

		// Mark all as read
		List<Message> messageList = new ArrayList<>(order2.get().getMessages());
		messageDao.markRead(messageList);

		// Retrieve the order again
		Optional<Order> order3 = orderDao.find(testPublication, testOrderer);

		// Check if all are read
		assertTrue(order3.isPresent());
		order3.get().getMessages().forEach(m -> assertTrue(m.getReadByReceiver()));

		// Remove all messages
		order3.get().removeMessage(testMessage);
		orderDao.update(order3.get());
	}

	@Test
	public void testGetUnread() {
		// Create new message in order
		Message testMessage = messageDao.createMessage(testOrder, testOrderer, testSupervisor, "Hi supervisor!");
		orderDao.update(testOrder);

		// Retrieve the order
		Optional<Order> order2 = orderDao.find(testPublication, testOrderer);

		// Check if order has messages
		assertEquals(order2.get().getMessages().size(), 1);

		// Retrieve unread messages for supervisor and orderer
		List<Message> supervisorUnreads = messageDao.getUnread(order2.get(), testSupervisor);
		List<Message> ordererUnreads = messageDao.getUnread(order2.get(), testOrderer);

		// Check if supervisor has one unread message
		assertEquals(supervisorUnreads.size(), 1);
		// Check if orderer has no unread messages
		assertEquals(ordererUnreads.size(), 0);

		// Mark all as read
		List<Message> messageList = new ArrayList<>(order2.get().getMessages());
		messageDao.markRead(messageList);

		// Retrieve the order again
		Optional<Order> order3 = orderDao.find(testPublication, testOrderer);

		// Retrieve unread messages for supervisor and orderer
		List<Message> supervisorUnreads2 = messageDao.getUnread(testOrder, testSupervisor);
		List<Message> ordererUnreads2 = messageDao.getUnread(testOrder, testSupervisor);

		// Check if supervisor has no unread messages
		assertEquals(supervisorUnreads2.size(), 0);
		// Check if orderer has no unread messages
		assertEquals(ordererUnreads2.size(), 0);

		// Remove all messages
		order3.get().removeMessage(testMessage);
		orderDao.update(order3.get());
	}

}
