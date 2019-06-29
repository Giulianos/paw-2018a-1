package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
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
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {TestConfig.class},
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class OrderServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private PublicationService publicationService;

  @Autowired
  private OrderService orderService;

  private User testSupervisor;
  private User testOrderer;

  @Before
  public void setupTest() {
    this.testSupervisor = userService.create("John Supervisor", "supervisor@example.com", "password123");
    this.testOrderer = userService.create("Will Orderer", "orderer@example.com", "password123");
  }

  @Test
  public void orderDecreasesAvailability() {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order
    Order order = orderService.create(testPublication, 5L);

    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    assertTrue(retrievedPublication.isPresent());
    assertEquals(retrievedPublication.get().getAvailableQuantity(), new Long(5L));
  }

  @Test(expected = PublicationFulfilledException.class)
  public void cantDeleteOrderOnFulfilledPublication() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Order
    Order supervisorOrder = orderService.create(testPublication, 5L);

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order (and fulfill publication)
    Order ordererOrder = orderService.create(testPublication, 5L);

    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    assertTrue(retrievedPublication.isPresent());

    // Try to delete order (this throws an exception)
    orderService.deleteByPublicationId(retrievedPublication.get().getId());
  }

  @Test
  public void confirmOrder() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order
    Order order = orderService.create(testPublication, 10L);

    // Login again as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Confirm order
    orderService.confirmOrderPurchase(order.getId());

    // Retrieve publication
    Optional<Publication> publication = publicationService.findById(testPublication.getId());

    // Check that all orders are confirmed
    publication.get().getOrders().forEach(o -> assertTrue(o.getPurchaseAccepted()));
  }

  @Test(expected = IllegalStateException.class)
  public void cantConfirmOrderOfNotFulfilledPublication() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order
    Order order = orderService.create(testPublication, 5L);

    // Confirm order
    orderService.confirmOrderPurchase(order.getId());
  }

}
