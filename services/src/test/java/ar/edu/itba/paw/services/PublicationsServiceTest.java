package ar.edu.itba.paw.services;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.interfaces.OrderService;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public class PublicationsServiceTest {
	private static final String [] SUPERVISOR = {"user1","user2","user3","user4","user5"};
	private static final String DESCRIPTION = "desc1";
	private static final String [] EMAIL = {"user1@example.com","user2@example.com","user3@example.com","user4@example.com","user5@example.com"};
	private static final float [] PRICE = {10.1f,13,13,13,13};
	private static final int [] QUANTITY = {10,14,16,16,16};
	private static final String PASSWORD = "pass";

	@Mock
	private PublicationDao publicationDao;

	@Mock
	private OrderService orders;
	
	@InjectMocks
	private PublicationServiceImpl publicationService;
	
	private User [] users;
	private Publication [] publications;
	
	@Before
	public void setUp() {
		publicationService = new PublicationServiceImpl();
		MockitoAnnotations.initMocks(this);

		users = new User[SUPERVISOR.length];
		publications = new Publication[SUPERVISOR.length];
		
		for (int i = 0; i < SUPERVISOR.length; i++) {
			users[i] = new User(SUPERVISOR[i], EMAIL[i], PASSWORD);
		}
		publications[0] = new Publication(null, DESCRIPTION, PRICE[0], QUANTITY[0]);
		publications[1] = new Publication(users[1], DESCRIPTION, PRICE[1], QUANTITY[1]);
		publications[2] = new Publication(users[2], DESCRIPTION, PRICE[2], QUANTITY[2]);
		publications[3] = new Publication(users[3], DESCRIPTION, PRICE[3], QUANTITY[3]);
		publications[4] = new Publication(null, DESCRIPTION, PRICE[4], QUANTITY[4]);
	}
	
	@Test
	public void test_findByDescription() {
		int fromIndex = 1;
		
		List<Publication> mockList = new ArrayList<Publication>();
		for (int i = 0; i < publications.length; i++) {
			mockList.add(publications[i]);
		}
		
		// mockList has 4 publications with the same description and only the second and third got a supervisor.
		// If I search starting at the 2nd element and I check for supervisors I should only get the 3rd publication.
		// This is because I first limit the list by publications with a supervisor and then I start looking
		// at the 2nd element from a list of 3
		
		// publicationDao only returns the middle elements from position 1 to 3
		// that have a supervisor.
		Mockito.when(publicationDao.findByDescription(DESCRIPTION, true))
			.thenReturn(mockList.subList(1, 4));
		
		List<Publication> pub = publicationService.findByDescription(DESCRIPTION, fromIndex, true, false);
		
		assertEquals(2,pub.size()); // Only have element 2nd and 3rd elements left.
		assertEquals(users[2],pub.get(0).getSupervisor());
		assertEquals(users[3],pub.get(1).getSupervisor());
	}
}