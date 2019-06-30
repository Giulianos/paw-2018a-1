package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = { TestConfig.class },
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class OrderHibernateDaoTest {

  @Autowired
  private UserDao userDao;
  @Autowired
  private PublicationDao publicationDao;
  @Autowired
  private OrderDao orderDao;

  private User testSupervisor;
  private User testOrderer;

  @Before
  public void testSetup() {
    this.testOrderer = userDao.create("John Orderer", "john.orderer@example.com", "password123");
    this.testSupervisor = userDao.create("Will Supervisor", "will.supervisor@example.com", "password123");
  }

  @Test
  public void canAddCalification() {
    // Create test publication and order
    Publication testPublication = publicationDao.create(testSupervisor, "publication", 1.0d, 10L, "");
    Order testOrder = orderDao.create(testPublication, testOrderer, 5L);

    // Check if order is persisted
    Optional<Order> retrievedOrder = orderDao.findById(testOrder.getId());
    assertTrue(retrievedOrder.isPresent());

    // Create review
    retrievedOrder.get().setReview(new Review("Excelent!", 5));
    orderDao.update(retrievedOrder.get());

    // Retrieve order
    Optional<Order> retrievedOrder2 = orderDao.findById(testOrder.getId());
    assertTrue(retrievedOrder2.isPresent());

    // Check if it has the created review
    assertNotNull(retrievedOrder2.get().getReview());
    assertEquals(retrievedOrder2.get().getReview().getComment(), "Excelent!");
    assertEquals(retrievedOrder2.get().getReview().getRating(), new Integer(5));
  }
}
