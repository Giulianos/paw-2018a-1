package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exception.PublicationFulfilledException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.PublicationState;
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
import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertFalse;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {TestConfig.class},
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class PublicationServiceImplTest {

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
  public void stateChangesToOrphanOnLeave() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order
    Order order = orderService.create(testPublication, 5L);

    // Login as supervisor again
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testSupervisor.getEmail())));

    // Supervisor leaves the publication
    publicationService.leavePublication(testPublication.getId());

    // Retrieve publication again
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    // Check if publication doesn't have supervisor and state is ORPHAN
    assertTrue(retrievedPublication.isPresent());
    assertNull(retrievedPublication.get().getSupervisor());
    assertEquals(retrievedPublication.get().getState(), PublicationState.ORPHAN);
  }

  @Test
  public void publicationIsDeletedOnLeaveWithoutOrders() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Supervisor leaves the publication
    publicationService.leavePublication(testPublication.getId());

    // Retrieve publication again
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    // Check if publication is not present
    assertFalse(retrievedPublication.isPresent());
  }

  @Test
  public void publicationIsDeletedOnLeaveWithSupervisorOrder() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test publication", 1.0d, 10L, "", new LinkedList<>());

    // Create order for supervisor
    Order order = orderService.create(testPublication, 5L);

    // Supervisor leaves the publication
    publicationService.leavePublication(testPublication.getId());

    // Retrieve publication again
    Optional<Publication> retrievePublication = publicationService.findById(testPublication.getId());

    assertFalse(retrievePublication.isPresent());
  }

  @Test(expected = UnauthorizedAccessException.class)
  public void nonSupervisorTriesToLeavePublication() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as other user
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Try to delete order (this should throw an exception)
    publicationService.leavePublication(testPublication.getId());
  }

  @Test
  public void adoptPublication() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Create order
    Order order = orderService.create(testPublication, 5L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Leave publication
    publicationService.leavePublication(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Adopt publication
    publicationService.adoptPublication(testPublication.getId());

    // Retrieve the publication
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    assertTrue(retrievedPublication.isPresent());

    // Check if the supervisor is the orderer
    assertEquals(retrievedPublication.get().getSupervisor(), testOrderer);
  }

  @Test
  public void publicationChangesToInProgressOnAdopt() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Create order
    Order order = orderService.create(testPublication, 5L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Leave publication
    publicationService.leavePublication(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Adopt publication
    publicationService.adoptPublication(testPublication.getId());

    // Retrieve the publication
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    assertTrue(retrievedPublication.isPresent());

    // Check if order state is IN PROGRESS
    assertEquals(retrievedPublication.get().getState(), PublicationState.IN_PROGRESS);
  }

  @Test
  public void publicationCanBeMarkedAsPurchased() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order everything
    Order order = orderService.create(testPublication, 10L);

    // Login as supervisor again
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testSupervisor.getEmail())));

    // Supervisor marks the publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Retrieve publication again
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    // Check if publication has state purchased
    assertTrue(retrievedPublication.isPresent());
    assertEquals(retrievedPublication.get().getState(), PublicationState.PURCHASED);
  }

  @Test(expected = IllegalStateException.class)
  public void cantMarkAsPurchasedIfNotFulfilled() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Supervisor tries to mark the publication as purchased
    publicationService.markAsPurchased(testPublication.getId());
  }

  @Test(expected =  UnauthorizedAccessException.class)
  public void onlySupervisorCanMarkPurchased() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order everything
    Order order = orderService.create(testPublication, 10L);

    // Orderer tries to mark the publication as purchased
    publicationService.markAsPurchased(testPublication.getId());
  }

  @Test
  public void allOrderConfirmedFinalizesPublication() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Order some units
    Order supervisorOrder = orderService.create(testPublication, 4L);

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Order everything
    Order order = orderService.create(testPublication, 6L);

    // Retrieve fulfilled publication
    Optional<Publication> fulfilledPublication = publicationService.findById(testPublication.getId());

    assertTrue(fulfilledPublication.isPresent());
    assertEquals(fulfilledPublication.get().getState(), PublicationState.FULFILLED);

    // Login as supervisor again
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testSupervisor.getEmail())));

    // Supervisor marks the publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Login as orderer again
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock((testOrderer.getEmail())));

    // Accept purchase
    orderService.confirmOrderPurchase(order.getId());

    // Retrieve publication again
    Optional<Publication> retrievedPublication = publicationService.findById(testPublication.getId());

    // Check if publication has state finalized
    assertTrue(retrievedPublication.isPresent());
    assertEquals(retrievedPublication.get().getState(), PublicationState.FINALIZED);
  }

  @Test
  public void retrievesUserPublications() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Retrieves supervisor publications
    List<Publication> supervisorPublications = publicationService.userPublications();

    // Check if there is just one and is the one we added
    assertEquals(supervisorPublications.size(), 1);
    assertEquals(supervisorPublications.get(0), testPublication);
  }

  @Test
  public void retrievesLatestPublications() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create some publications
    Publication testPublication1 = publicationService.create("Test Publication 1", 1.0d, 10L, "", new LinkedList<>());
    Publication testPublication2 = publicationService.create("Test Publication 2", 1.0d, 10L, "", new LinkedList<>());
    Publication testPublication3 = publicationService.create("Test Publication 3", 1.0d, 10L, "", new LinkedList<>());

    // Logout
    SecurityContextHolder.getContext().setAuthentication(null);

    // Retrieves latest 2 publications
    List<Publication> latestPublications = publicationService.latest(2);

    // Check if there is just two
    assertEquals(latestPublications.size(), 2);

    // Check if they are in the correct order
    assertEquals(latestPublications.get(0), testPublication3);
    assertEquals(latestPublications.get(1), testPublication2);
  }

}
