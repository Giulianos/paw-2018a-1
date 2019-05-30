package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.exception.EntityNotFoundException;
import ar.edu.itba.paw.interfaces.exception.UnauthorizedAccessException;
import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.interfaces.service.PublicationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Image;
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
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = { TestConfig.class },
    loader = AnnotationConfigContextLoader.class)
@Transactional
public class ImageServiceImplTest {

  @Autowired
  private UserService userService;

  @Autowired
  private PublicationDao publicationDao;

  @Autowired
  private ImageService imageService;

  @Autowired
  private PublicationService publicationService;

  private User testUser;

  @Before
  public void setupTest() {
    this.testUser = userService.create("John Doe", "john.doe@example.com", "password123");
  }

  @Test
  public void findImageById() throws Exception {
    // Create test publication
    Publication testPublication = publicationDao.create(testUser, "Test Publication",1.0, 2L, "");

    // Create image in testPublication
    Optional<Image> image = publicationService.addImage(testUser.getEmail(), testPublication.getId(), "imageInBase64");

    // Find image by id
    Optional<Image> foundImage = imageService.findById(image.get().getId());

    // Check if it's present and it's the one we created
    assertTrue(foundImage.isPresent());
    assertEquals(foundImage.get(), image.get());

  }

  @Test(expected = UnauthorizedAccessException.class)
  public void unauthorizedAccessException() throws UnauthorizedAccessException, EntityNotFoundException {
    // Create test publication
    Publication testPublication = publicationDao.create(testUser, "Test Publication",1.0, 2L, "");

    // Create image in testPublication
    Optional<Image> image = publicationService.addImage("other@email.com", testPublication.getId(), "imageInBase64");
  }

  @Test(expected = EntityNotFoundException.class)
  public void entityNotFoundException() throws UnauthorizedAccessException, EntityNotFoundException {
    // Create image in non-existent publication
    Optional<Image> image = publicationService.addImage("other@email.com", -1L, "imageInBase64");
  }
}
