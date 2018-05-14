package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PublicationJdbcDaoTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] SUPERVISOR = {"user1","user2"};
	private static final String [] DESCRIPTION = {"desc1","desc2"};
	private static final String IMAGE = "";
	private static final int INT_VALUE = 10;
	private static final float FLOAT_VALUE = 10.1f;
	
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
		
		// Create users prior to publications in accordance to foreign key restrictions.
		for (int i = 0; i < SUPERVISOR.length; i++) {
			userDao.create(SUPERVISOR[i], EMAIL[i], PASSWORD);
		}
	}
	
	@Test
	public void test1_create() {
		Publication [] publications = {null, null};
		
		for (int i = 0; i < publications.length; i++) {
			publications[i] = publicationDao.create(SUPERVISOR[i], DESCRIPTION[i], FLOAT_VALUE, INT_VALUE, IMAGE);
			assertNotNull(publications[i]);
			assertEquals(SUPERVISOR[i], publications[i].getSupervisor());
			assertEquals(DESCRIPTION[i], publications[i].getDescription());
		}
		assertEquals(publications.length, JdbcTestUtils.countRowsInTable(jdbcTemplate, "publications"));
	}
	
	// To check duplicate key exception.
	
	@Test
	public void test2_findBySupervisor() {
		//List<Publication> publications = publicationDao.findBySupervisor(SUPERVISOR[0]);
		//assertFalse(publications.isEmpty());
		//assertEquals(SUPERVISOR[0], publications.get(0));
	}
}