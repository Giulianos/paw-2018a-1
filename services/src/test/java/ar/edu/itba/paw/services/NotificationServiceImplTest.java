package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.services.mocks.AuthenticationMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {TestConfig.class},
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class NotificationServiceImplTest {

  @Autowired
  private NotificationService notificationService;
  @Autowired
  private UserService userService;
  @Autowired
  private PublicationService publicationService;
  @Autowired
  private OrderService orderService;

  private User testOrderer;
  private User testSupervisor;

  @Before
  public void setupTest() {
    // Create test users
    testOrderer = userService.create("John Orderer", "john.orderer@example.org", "pass123");
    testSupervisor = userService.create("Will Supervisor", "will.supervisor@example.org", "pass123");
  }

  @Test
  public void publicationFulFilledTriggersNotification() {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create test publication
    Publication testPublication = publicationService.create("Test publication", 1d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    // Create order
    Order ordererOrder = orderService.create(testPublication, 5L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create order (and fulfill publication)
    Order supervisorOrder = orderService.create(testPublication, 5L);

    // Retrieve supervisor notifications
    List<Notification> supervisorNotifications = notificationService.getLatest();
    // Check that it has a Publication fulfilled notification
    assertEquals(supervisorNotifications.size(), 1);
    assertEquals(supervisorNotifications.get(0).getType(), NotificationType.PUBLICATION_FULFILLED);
    // The notification should have a related publication
    assertEquals(supervisorNotifications.get(0).getRelatedPublication(), testPublication);
    // The notification for the supervisor shouldn't have a related order (even though it has one)
    assertNull(supervisorNotifications.get(0).getRelatedOrder());

    // Retrieve orderer notifications
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    List<Notification> ordererNotifications = notificationService.getLatest();
    // Check that it has a Publication fulfilled notification
    assertEquals(ordererNotifications.size(), 1);
    assertEquals(ordererNotifications.get(0).getType(), NotificationType.PUBLICATION_FULFILLED);
    // The notification should have a related order and publication
    assertEquals(ordererNotifications.get(0).getRelatedPublication(), testPublication);
    assertEquals(ordererNotifications.get(0).getRelatedOrder(), ordererOrder);
  }

  @Test
  public void publicationPurchasedTriggersNotification() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create test publication
    Publication testPublication = publicationService.create("Test publication", 1d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    // Create order
    Order ordererOrder = orderService.create(testPublication, 5L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create order (and fulfill publication)
    Order supervisorOrder = orderService.create(testPublication, 5L);
    // Mark the publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Retrieve supervisor notifications
    List<Notification> supervisorNotifications = notificationService.getLatest();
    // Check that it has a Publication fulfilled notification (it doesn't receive the publication purchased notification)
    assertEquals(supervisorNotifications.size(), 1);
    assertEquals(supervisorNotifications.get(0).getType(), NotificationType.PUBLICATION_FULFILLED);
    // The notification should have a related publication
    assertEquals(supervisorNotifications.get(0).getRelatedPublication(), testPublication);
    // The notification for the supervisor shouldn't have a related order (even though it has one)
    assertNull(supervisorNotifications.get(0).getRelatedOrder());

    // Retrieve orderer notifications
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    List<Notification> ordererNotifications = notificationService.getLatest();
    // Check that it has a 2 notifications and the last one is the Publication purchased notification
    assertEquals(ordererNotifications.size(), 2);
    assertEquals(ordererNotifications.get(0).getType(), NotificationType.ORDER_PURCHASED);
    // The notification should have a related order and publication
    assertEquals(ordererNotifications.get(0).getRelatedPublication(), testPublication);
    assertEquals(ordererNotifications.get(0).getRelatedOrder(), ordererOrder);
  }

  @Test
  public void supervisorLeftTriggersNotification() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create test publication
    Publication testPublication = publicationService.create("Test publication", 1d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    // Create order
    Order ordererOrder = orderService.create(testPublication, 5L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Leave publication
    publicationService.leavePublication(testPublication.getId());

    // Retrieve orderer notifications
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    List<Notification> ordererNotifications = notificationService.getLatest();
    // Check that it has the Publication orphan notification
    assertEquals(ordererNotifications.size(), 1);
    assertEquals(ordererNotifications.get(0).getType(), NotificationType.PUBLICATION_ORPHAN);
    // The notification should have a related order and publication
    assertEquals(ordererNotifications.get(0).getRelatedPublication(), testPublication);
    assertEquals(ordererNotifications.get(0).getRelatedOrder(), ordererOrder);
  }

  @Test
  public void multipleMessagesTriggersOneNotification() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    // Create test publication
    Publication testPublication = publicationService.create("Test publication", 1d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));
    // Create order
    Order ordererOrder = orderService.create(testPublication, 5L);

    // Send multiple messages
    orderService.sendMessage(ordererOrder.getId(), "Hi supervisor!");
    orderService.sendMessage(ordererOrder.getId(), "This is another message");

    // Retrieve supervisor notifications
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));
    List<Notification> supervisorNotifications = notificationService.getLatest();
    // Check that it has just one new message notification
    assertEquals(supervisorNotifications.size(), 1);
    assertEquals(supervisorNotifications.get(0).getType(), NotificationType.NEW_MESSAGES);
    // The notification should have a related order (the one the message was sent to)
    assertEquals(supervisorNotifications.get(0).getRelatedOrder(), ordererOrder);
  }
}
