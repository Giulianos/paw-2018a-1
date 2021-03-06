package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.OrderService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.ReviewService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.Review;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

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
  public void cannotReviewConfirmedOrder() throws Exception {
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

    // Try to review supervisor after confirming order purchase
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

    // Try to review with less than 1
    reviewService.reviewOrder(testOrder.getId(), "Very bad!", 0);
  }

  @Test
  public void canRetrieveUserReviews() throws Exception {
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

    // Review
    reviewService.reviewOrder(testOrder.getId(), "Good!", 3);

    // Login as supervisor
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testSupervisor.getEmail()));

    // Retrieve reviews
    List<Review> reviews  = reviewService.getUserReviews(testSupervisor.getId());

    assertEquals(reviews.size(), 1);
    assertEquals(reviews.get(0).getComment(), "Good!");
    assertEquals(reviews.get(0).getRating(), new Integer(3));
  }

  @Test(expected = UnauthorizedAccessException.class)
  public void usersCannotRetrieveOthersReviews() throws Exception {
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

    // Review
    reviewService.reviewOrder(testOrder.getId(), "Good!", 3);

    // Try to retrieve supervisor reviews
    List<Review> reviews  = reviewService.getUserReviews(testSupervisor.getId());
  }

  @Test
  public void canRetrieveUserRating() throws Exception {
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

    // Review
    reviewService.reviewOrder(testOrder.getId(), "Good!", 5);

    // Retrieve rating
    final Integer rating  = reviewService.getUserRating(testSupervisor.getId());

    assertEquals(rating, new Integer(5));
  }

  @Test
  public void didReviewReturnsTrueIfReviewForOrderExists() throws Exception {
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

    // Login as orderer
    SecurityContextHolder.getContext().setAuthentication(new AuthenticationMock(testOrderer.getEmail()));

    // Review
    reviewService.reviewOrder(testOrder.getId(), "Good!", 3);

    Boolean didReviewOrder = orderService.didReviewOrder(testOrder.getId());

    assertTrue(didReviewOrder);
  }
}
