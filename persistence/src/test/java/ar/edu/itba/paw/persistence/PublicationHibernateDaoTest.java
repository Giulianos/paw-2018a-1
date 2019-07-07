package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.OrderDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
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
public class PublicationHibernateDaoTest {

  @Autowired
  private UserDao userDao;
  @Autowired
  private PublicationDao publicationDao;

  private User testSupervisor;
  private User testSupervisor2;

  @Before
  public void testSetup() {
    this.testSupervisor = userDao.create("Will Supervisor", "will.supervisor@example.com", "password123");
    this.testSupervisor2 = userDao.create("John Supervisor", "john.supervisor@example.com", "password123");
  }

  @Test
  public void retrievesLatestPublications() {
    // Create test publications
    Publication testPublication1 = publicationDao.create(testSupervisor, "publication", 1.0d, 10L, "");
    Publication testPublication2 = publicationDao.create(testSupervisor2, "publication 2", 1.0d, 10L, "");

    // Retrieve latest publications
    List<Publication> latestPublications = publicationDao.latestPublications(2);

    // Check if it has two publications
    assertEquals(latestPublications.size(), 2);

    // Check if they are correctly ordered
    assertEquals(latestPublications.get(0), testPublication2);
    assertEquals(latestPublications.get(1), testPublication1);
  }

  @Test
  public void retrievesUserPublications() {
    // Create test publications
    Publication testPublication1 = publicationDao.create(testSupervisor, "publication", 1.0d, 10L, "");
    Publication testPublication2 = publicationDao.create(testSupervisor2, "publication 2", 1.0d, 10L, "");

    // Retrieve user publications
    List<Publication> supervisor1Publications = publicationDao.userPublications(testSupervisor.getEmail());

    // Check if it has one publications
    assertEquals(supervisor1Publications.size(), 1);

    // Check if the correct one
    assertEquals(supervisor1Publications.get(0), testPublication1);
  }
}
