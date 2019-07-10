package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.NotificationDao;
import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = { TestConfig.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class NotificationHibernateDaoTest {

	@Autowired
	private NotificationDao notificationDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private PublicationDao publicationDao;
	@Autowired
	private OrderDao orderDao;

	private User testUser;
	private Order testOrder;
	private Publication testPublication;

	@Before
	public void setupTest() {
		testUser = userDao.create("John Doe", "john.doe@example.com", "pass123");
		testPublication = publicationDao.create(testUser, "Test Publication", 10d, 10L, "");
		testOrder = orderDao.create(testPublication, testUser, 5L);
	}

	@Test
	public void retrieveLatest() {
		// Create some notifications
		Notification first = notificationDao.create(testUser, NotificationType.NEW_MESSAGES, testPublication, testOrder, null);
		Notification second = notificationDao.create(testUser, NotificationType.ORDER_PURCHASED, null, testOrder, null);
		Notification third = notificationDao.create(testUser, NotificationType.PUBLICATION_FULFILLED, testPublication, null, null);

		// Retrieve user notifications
		List<Notification> notifications = notificationDao.getLatest(testUser.getId(), 2);

		// Check if we retrieved last 2 notifications
		assertEquals(notifications.size(), 2);
		assertEquals(notifications.get(0), third);
		assertEquals(notifications.get(1), second);
	}

	@Test
	public void retriveUnseen() {
		// Create some notifications
		Notification firstNotification = notificationDao.create(testUser, NotificationType.NEW_MESSAGES, testPublication, testOrder, null);
		Notification secondNotification = notificationDao.create(testUser, NotificationType.ORDER_PURCHASED, null, testOrder, null);
		Notification thirdNotification = notificationDao.create(testUser, NotificationType.PUBLICATION_FULFILLED, testPublication, null, null);

		// Mark first notification as seen
		firstNotification.setSeen(true);
		notificationDao.update(firstNotification);

		// Retrieve unseen user notifications
		List<Notification> notifications = notificationDao.getUnseen(testUser.getId());

		// Check if we retrieved last 2 notifications
		assertEquals(notifications.size(), 2);
	}
}
