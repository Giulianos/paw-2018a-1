package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.dao.ReviewDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = { TestConfig.class },
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class ReviewHibernateDaoTest {

  @Autowired
  private UserDao userDao;
  @Autowired
  private PublicationDao publicationDao;
  @Autowired
  private OrderDao orderDao;
  @Autowired
  private ReviewDao reviewDao;

  private User testSupervisor;
  private User testOrderer;

  @Before
  public void testSetup() {
    this.testOrderer = userDao.create("John Orderer", "john.orderer@example.com", "password123");
    this.testSupervisor = userDao.create("Will Supervisor", "will.supervisor@example.com", "password123");
  }

  @Test
  public void fetchesSupervisorReviews() {
    // Create test publication and order
    Publication testPublication = publicationDao.create(testSupervisor, "publication", 1.0d, 10L, "");
    Order testOrder = orderDao.create(testPublication, testOrderer, 5L);

    // Check if order is persisted
    Optional<Order> retrievedOrder = orderDao.findById(testOrder.getId());
    assertTrue(retrievedOrder.isPresent());

    // Create review
    retrievedOrder.get().setReview(new Review("Excelent!", 5));
    orderDao.update(retrievedOrder.get());

    // Retrieve supervisor reviews
    List<Review> supervisorReviews = reviewDao.userReviews(testSupervisor.getId());

    // Check that it has one review
    assertEquals(supervisorReviews.size(), 1);

    // Check if it is the one we created
    assertEquals(supervisorReviews.get(0).getComment(), "Excelent!");
    assertEquals(supervisorReviews.get(0).getRating(), new Integer(5));
  }
}
