package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.dao.ImageDao;
import ar.edu.itba.paw.interfaces.dao.PublicationDao;
import ar.edu.itba.paw.interfaces.dao.UserDao;
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

import static junit.framework.TestCase.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		classes = { TestConfig.class },
		loader = AnnotationConfigContextLoader.class)
@Transactional
public class ImageHibernateDaoTest {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PublicationDao publicationDao;

	@Autowired
	private ImageDao imageDao;

	private User testUser;
	private Publication testPublication;

	@Before
	public void testSetup() {
		this.testUser = userDao.create("John Doe", "john.doe@example.com", "password123");
		this.testPublication = publicationDao.create(testUser, "Test Publication", 10.0, 2L, "");
	}

	@Test
	public void addImageToPublicationThenRetrieve() {
		// Create image in testPublication
		Image image = imageDao.addToPublication(testPublication, "imageInBase64");
		publicationDao.update(testPublication);

		// Get publication from dao
		Publication publication = publicationDao.findById(testPublication.getId()).get();

		// Get image from publication
		Optional<Image> publicationImage = publication.getImages().stream().findFirst();

		// Check if it's present and it's the one we created
		assertTrue(publicationImage.isPresent());
		assertEquals(publicationImage.get(), image);
	}
}
