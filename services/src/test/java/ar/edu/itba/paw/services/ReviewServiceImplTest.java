package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = {TestConfig.class},
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class ReviewServiceImplTest {

  @Autowired
  private ReviewService reviewService;
  @Autowired
  private UserService userService;
  @Autowired
  private OrderService orderService;
  @Autowired
  private PublicationService publicationService;

  private User testSupervisor;
  private User testOrderer;

  @Before
  public void setupTest() {
   testOrderer = userService.create("John Supervisor", "john.supervisor@example.com", "pass1234");
   testSupervisor = userService.create("Will Orderer", "will.supervisor@example.com", "pass1234");
  }

  @Test(expected = IllegalStateException.class)
  public void cannotReviewUnconfirmedOrder() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order all products
    Order testOrder = orderService.create(testPublication, 10L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Try to review supervisor without confirming order purchase
    reviewService.reviewOrder(testOrder.getId(), "Test review", 4);
  }

  @Test(expected = UnauthorizedAccessException.class)
  public void onlyOrdererCanReview() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order all products
    Order testOrder = orderService.create(testPublication, 10L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Try to review order as supervisor
    reviewService.reviewOrder(testOrder.getId(), "Excelent!", 5);
  }

  @Test(expected = IllegalStateException.class)
  public void supervisorCannotReviewHimself() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Order all products as supervisor
    Order testSupervisorOrder = orderService.create(testPublication, 10L);

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Supervisor tries to review himself
    reviewService.reviewOrder(testSupervisorOrder.getId(), "Excelent!", 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ratingShouldBe5AtMost() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order all products
    Order testOrder = orderService.create(testPublication, 10L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Confirm order
    orderService.confirmOrderPurchase(testOrder.getId());

    // Try to review with more than 5
    reviewService.reviewOrder(testOrder.getId(), "Awesome!", 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void ratingShouldBeAtLeast1() throws Exception {
    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Create publication
    Publication testPublication = publicationService.create("Test Publication", 1.0d, 10L, "", new LinkedList<>());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Order all products
    Order testOrder = orderService.create(testPublication, 10L);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Mark publication as purchased
    publicationService.markAsPurchased(testPublication.getId());

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Confirm order
    orderService.confirmOrderPurchase(testOrder.getId());

    // Try to review with less than 1
    reviewService.reviewOrder(testOrder.getId(), "Very bad!", 0);
  }
}
