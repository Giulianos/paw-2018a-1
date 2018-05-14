package ar.edu.itba.paw.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
public class PublicationJdbcDaoCreateTest {
	private static final String PASSWORD = "pass";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	
	private static final String [] SUPERVISOR = {"user1","user2"};
	private static final String [] DESCRIPTION = {"desc1","desc2"};
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
	}
	
	@Test
	public void test_create() {
		Publication [] publications = {null, null};
		
		for (int i = 0; i < publications.length; i++) {
			publications[i] = publicationDao.create(SUPERVISOR[i], DESCRIPTION[i], PRICE[i], QUANTITY[i], IMAGE);
			assertNotNull(publications[i]);
			assertEquals(SUPERVISOR[i], publications[i].getSupervisor());
			assertEquals(DESCRIPTION[i], publications[i].getDescription());
		}
		assertEquals(publications.length, JdbcTestUtils.countRowsInTable(jdbcTemplate, "publications"));
	}
}