package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import ar.edu.itba.paw.model.Publication;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Rollback
public class PublicationJdbcDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] SUPERVISOR = {"user1","user2"};
	private static final String [] DESCRIPTION = {"desc1","desc2"};
	private static final String [] SUB_DESCRIPTION = {"des","esc","desc"};
	private static final String DESCRIPTION_UNKNOWN = "desc12";
	private static final String IMAGE = "";
	private static final float [] PRICE = {10.1f,13};
	private static final int [] QUANTITY = {10,14};
	
	@Autowired
	private DataSource ds;
	@Autowired
	private UserJdbcDao userDao;
	@Autowired
	private PublicationJdbcDao publicationDao;
	private JdbcTemplate jdbcTemplate;
	
	
	@Before
	public void setUp() {
		jdbcTemplate = new JdbcTemplate(ds);
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
		JdbcTestUtils.deleteFromTables(jdbcTemplate, "publications");
		// Create users prior to publications in accordance to foreign key restrictions.
		for (int i = 0; i < SUPERVISOR.length; i++) {
			userDao.create(SUPERVISOR[i], EMAIL[i], PASSWORD);
		}
		for (int i = 0; i < SUPERVISOR.length; i++) {
			publicationDao.create(SUPERVISOR[i], DESCRIPTION[i], PRICE[i], QUANTITY[i], IMAGE);
		}
	}
	
	@Test
	public void test_findBySupervisor() {
		List<Publication> publications;
		
		for (int i = 0; i < SUPERVISOR.length; i++) {
			publications = publicationDao.findBySupervisor(SUPERVISOR[i]);
			assertFalse(publications.isEmpty());
			
			for (int j = 0; j < publications.size(); j++) {
				assertEquals(SUPERVISOR[i], publications.get(j).getSupervisor());
			}
		}
	}
	
	@Test
	public void test_findById() {
		List<Publication> publications = publicationDao.findBySupervisor(SUPERVISOR[0]);
		assertFalse(publications.isEmpty());
		long id = publications.get(0).getId();
		
		Optional<Publication> publication = publicationDao.findById(id);
		assertTrue(publication.isPresent());
		assertEquals(id, publication.get().getId());
	}
	
	@Test
	public void test_findByDescription() {
		List<Publication> publications;
		// Sub descriptions used for test were selected
		// so they would match every description.
		for (int i = 0; i < SUB_DESCRIPTION.length; i++) {
			publications = publicationDao.findByDescription(SUB_DESCRIPTION[i]);
			assertFalse(publications.isEmpty());
			assertEquals(DESCRIPTION.length, publications.size());
		}
		// Since all publications in the test have different descriptions,
		// for 1 of those descriptions there should be only 1 match.
		for (int i = 0; i < DESCRIPTION.length; i++) {
			publications = publicationDao.findByDescription(DESCRIPTION[i]);
			assertFalse(publications.isEmpty());
			assertEquals(1, publications.size());
			assertEquals(DESCRIPTION[i], publications.get(0).getDescription());
		}
		// Unknown description is larger than any of the provided descriptions
		// so it should return no match.
		assertTrue(publicationDao.findByDescription(DESCRIPTION_UNKNOWN).isEmpty());
	}
	
	@Test
	public void test_findByPriceRange() {
		// PRICE is ordered from lower to higher so if I search by the min and max
		// values, the original list should be obtained.
		List<Publication> publications = publicationDao.findByPrice(PRICE[0], PRICE[PRICE.length-1]);
		assertFalse(publications.isEmpty());
		assertEquals(PRICE.length, publications.size());
		
		// If I test with a range that includes mine the result should be the same as before.
		publications = publicationDao.findByPrice(PRICE[0]-0.1f, PRICE[PRICE.length-1]+0.1f);
		assertFalse(publications.isEmpty());
		assertEquals(PRICE.length, publications.size());
		
		// If I test with a range that inside mine there should be no match.
		publications = publicationDao.findByPrice(PRICE[0]+0.1f, PRICE[PRICE.length-1]-0.1f);
		assertTrue(publications.isEmpty());
		
		// If I test up to min bound from left or up to max bound from right,
		// there should be only 1 match.
		publications = publicationDao.findByPrice(PRICE[0]-0.1f, PRICE[0]);
		assertFalse(publications.isEmpty());
		assertEquals(1, publications.size());
		
		publications = publicationDao.findByPrice(PRICE[PRICE.length-1], PRICE[PRICE.length-1]+0.1f);
		assertFalse(publications.isEmpty());
		assertEquals(1, publications.size());
	}
	
	@Test
	public void test_findByQuantityRange() {
		// QUANTITY is ordered from lower to higher so if I search by the min and max
		// values, the original list should be obtained.
		List<Publication> publications = publicationDao.findByQuantity(QUANTITY[0], QUANTITY[QUANTITY.length-1]);
		assertFalse(publications.isEmpty());
		assertEquals(QUANTITY.length, publications.size());
		
		// If I test with a range that includes mine the result should be the same as before.
		publications = publicationDao.findByQuantity(QUANTITY[0]-1, QUANTITY[QUANTITY.length-1]+1);
		assertFalse(publications.isEmpty());
		assertEquals(QUANTITY.length, publications.size());
		
		// If I test with a range that inside mine there should be no match.
		publications = publicationDao.findByQuantity(QUANTITY[0]+1, QUANTITY[QUANTITY.length-1]-1);
		System.out.println(publications.isEmpty());
		assertTrue(publications.isEmpty());
		
		// If I test up to min bound from left or up to max bound from right,
		// there should be only 1 match.
		publications = publicationDao.findByQuantity(QUANTITY[0]-1, QUANTITY[0]);
		assertFalse(publications.isEmpty());
		assertEquals(1, publications.size());

		publications = publicationDao.findByQuantity(QUANTITY[QUANTITY.length-1], QUANTITY[QUANTITY.length-1]+1);
		assertFalse(publications.isEmpty());
		assertEquals(1, publications.size());
	}
	
	@Test
	public void test_findByQuantity() {
		List<Publication> publications;
		
		for (int i = 0; i < QUANTITY.length; i++) {
			publications = publicationDao.findByQuantity(QUANTITY[i]);
			assertFalse(publications.isEmpty());
		}
		
		publications = publicationDao.findByQuantity(QUANTITY[0]-1);
		assertTrue(publications.isEmpty());
		
		publications = publicationDao.findByQuantity(QUANTITY[QUANTITY.length-1]+1);
		assertTrue(publications.isEmpty());
	}
	
	@Test
	public void test_confirm() {
		List<Publication> publications = publicationDao.findBySupervisor(SUPERVISOR[0]);
		assertFalse(publications.isEmpty());
		long id = publications.get(0).getId();
		assertFalse(publications.get(0).getConfirmed());
		publicationDao.confirm(id);
		assertTrue(publicationDao.findById(id).get().getConfirmed());
	}
	
	@Test
	public void test_delete() {
		List<Publication> publications = publicationDao.findBySupervisor(SUPERVISOR[0]);
		assertFalse(publications.isEmpty());
		long id = publications.get(0).getId();
		
		assertTrue(publicationDao.findById(id).isPresent());
		publicationDao.delete(id);
		assertFalse(publicationDao.findById(id).isPresent());
	}
}