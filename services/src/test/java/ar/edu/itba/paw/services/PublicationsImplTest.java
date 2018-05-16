package ar.edu.itba.paw.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.interfaces.ConfirmedOrders;
import ar.edu.itba.paw.interfaces.Orders;
import ar.edu.itba.paw.interfaces.PublicationDao;
import ar.edu.itba.paw.model.ConfirmedOrder;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = TestConfig.class)
public class PublicationsImplTest {
	private static final int [] ID = {1,2};
	private static final String [] SUPERVISOR = {"user1","user2"};
	private static final String [] DESCRIPTION = {"desc1","desc2"};
	private static final String SUB_DESCRIPTION = "des";
	private static final String USERNAME_UNKNOWN = "userx";
	private static final String DESCRIPTION_UNKNOWN = "desc12";
	private static final String IMAGE = "";
	private static final float [] PRICE = {10.1f,13};
	private static final int [] QUANTITY = {10,14};

	@Mock
	private PublicationDao publicationDao;

	@Mock
	private Orders orders;

	@Mock
	private ConfirmedOrders confirmedOrders;

	// Unable to autowire. Context problem. To check.
	
	@InjectMocks
	//@Autowired
	private PublicationsImpl publications;
	
	@Before
	public void setUp() {
		publications = new PublicationsImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_findBySupervisor() {
		List<Publication> mockList = new ArrayList<Publication>();
		mockList.add(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false));
		
		Mockito.when(publicationDao.findBySupervisor(SUPERVISOR[0]))
			.thenReturn(mockList);		
		Mockito.when(publicationDao.findBySupervisor(USERNAME_UNKNOWN))
			.thenReturn(new ArrayList<Publication>());
		
		List<Publication> pub = publications.findBySupervisor(SUPERVISOR[0]);
		assertFalse(pub.isEmpty());
		assertEquals(ID[0],pub.get(0).getId());
		assertEquals(SUPERVISOR[0],pub.get(0).getSupervisor());
		assertEquals(DESCRIPTION[0],pub.get(0).getDescription());
		assertEquals(PRICE[0],pub.get(0).getPrice(),0);
		assertEquals(QUANTITY[0],pub.get(0).getQuantity());
		assertEquals(false,pub.get(0).getConfirmed());
		
		pub = publications.findBySupervisor(USERNAME_UNKNOWN);
		assertTrue(pub.isEmpty());
	}
	
	@Test
	public void test_findById() {
		Mockito.when(publicationDao.findById(ID[0]))
			.thenReturn(Optional.of(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false)));
		
		Optional<Publication> pub = publications.findById(ID[0]);
		assertTrue(pub.isPresent());
		assertEquals(ID[0],pub.get().getId());
		assertEquals(SUPERVISOR[0],pub.get().getSupervisor());
		assertEquals(DESCRIPTION[0],pub.get().getDescription());
		assertEquals(PRICE[0],pub.get().getPrice(),0);
		assertEquals(QUANTITY[0],pub.get().getQuantity());
		assertEquals(false,pub.get().getConfirmed());
	}
	
	@Test
	public void test_findByDescription() {
		List<Publication> mockList = new ArrayList<Publication>();
		mockList.add(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false));
		
		Mockito.when(publicationDao.findByDescription(SUB_DESCRIPTION))
			.thenReturn(mockList);		
		Mockito.when(publicationDao.findByDescription(DESCRIPTION_UNKNOWN))
			.thenReturn(new ArrayList<Publication>());
		
		List<Publication> pub = publications.findByDescription(SUB_DESCRIPTION);
		assertFalse(pub.isEmpty());
		assertEquals(ID[0],pub.get(0).getId());
		assertEquals(SUPERVISOR[0],pub.get(0).getSupervisor());
		assertEquals(DESCRIPTION[0],pub.get(0).getDescription());
		assertEquals(PRICE[0],pub.get(0).getPrice(),0);
		assertEquals(QUANTITY[0],pub.get(0).getQuantity());
		assertEquals(false,pub.get(0).getConfirmed());
		
		pub = publications.findBySupervisor(DESCRIPTION_UNKNOWN);
		assertTrue(pub.isEmpty());
	}
	
	@Test
	public void test_findByDescriptionAndSupervisor() {
		List<Publication> mockList = new ArrayList<Publication>();
		
		Mockito.when(publicationDao.findByDescription(SUB_DESCRIPTION,true))
			.thenReturn(mockList);
		
		List<Publication> pub = publications.findByDescription(SUB_DESCRIPTION);
		assertTrue(pub.isEmpty());
	}
	
	@Test
	public void test_findByPrice() {
		List<Publication> mockList = new ArrayList<Publication>();
		mockList.add(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false));
		mockList.add(new Publication(ID[1],SUPERVISOR[1],DESCRIPTION[1],PRICE[1],QUANTITY[1],IMAGE,false));
		
		Mockito.when(publicationDao.findByPrice(PRICE[0],PRICE[1]))
			.thenReturn(mockList);
		
		List<Publication> pub = publications.findByPrice(PRICE[0],PRICE[1]);
		assertFalse(pub.isEmpty());
		assertEquals(mockList.size(),pub.size());
	}
	
	@Test
	public void test_findByQuantity() {
		List<Publication> mockList = new ArrayList<Publication>();
		mockList.add(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false));
		mockList.add(new Publication(ID[1],SUPERVISOR[1],DESCRIPTION[1],PRICE[1],QUANTITY[1],IMAGE,false));
		List<Publication> mockList2 = new ArrayList<Publication>();
		mockList2.add(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false));
		
		Mockito.when(publicationDao.findByQuantity(QUANTITY[0],QUANTITY[1]))
			.thenReturn(mockList);
		Mockito.when(publicationDao.findByQuantity(QUANTITY[0]))
			.thenReturn(mockList2);
		
		List<Publication> pub = publications.findByQuantity(QUANTITY[0],QUANTITY[1]);
		assertFalse(pub.isEmpty());
		assertEquals(mockList.size(),pub.size());
		
		pub = publications.findByQuantity(QUANTITY[0]);
		assertFalse(pub.isEmpty());
		assertEquals(mockList2.size(),pub.size());
	}
	
	@Test
	public void test_create() {
		Mockito.when(publicationDao.create(SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE))
			.thenReturn(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE));
		
		Publication pub = publications.create(SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE);
		assertNotNull(pub);
		assertEquals(ID[0],pub.getId());
		assertEquals(SUPERVISOR[0],pub.getSupervisor());
		assertEquals(DESCRIPTION[0],pub.getDescription());
		assertEquals(PRICE[0],pub.getPrice(),0);
		assertEquals(QUANTITY[0],pub.getQuantity());
	}
	
	@Test
	public void test_remainingQuantity() {
		List<Order> ord = new ArrayList<Order>();
		ord.add(new Order(ID[0],SUPERVISOR[0],QUANTITY[0],false));
		
		Mockito.when(publicationDao.findById(ID[0]))
			.thenReturn(Optional.of(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0]+QUANTITY[1],IMAGE,false)));
		Mockito.when(orders.findByPublicationId(ID[0]))
			.thenReturn(ord);
		
		int remainingQuantity = publications.remainingQuantity(ID[0]);
		assertEquals(QUANTITY[1],remainingQuantity);
	}
	
	@Test
	public void test_confirm() {
		List<Order> ord = new ArrayList<Order>();
		ord.add(new Order(ID[0],SUPERVISOR[0],QUANTITY[0],false));
		
		Mockito.when(publicationDao.findById(ID[0]))
			.thenReturn(Optional.of(new Publication(ID[0],SUPERVISOR[0],DESCRIPTION[0],PRICE[0],QUANTITY[0],IMAGE,false)));
		Mockito.when(orders.findByPublicationId(ID[0]))
			.thenReturn(ord);
		// Dao confirm method is reached if remaining quantity == 0.
		Mockito.when(publicationDao.confirm(ID[0]))
			.thenReturn(true);
		
		assertEquals(true,publications.confirm(ID[0]));
	}
	
	@Test
	public void test_confirmOrders() {
		Mockito.when(orders.areConfirmed(ID[0]))
			.thenReturn(true)
				.thenReturn(false);
		Mockito.when(orders.findByPublicationId(ID[0]))
			.thenReturn(new ArrayList<Order>());
		Mockito.when(orders.delete(ID[0]))
			.thenReturn(true);
		
		assertEquals(true,publications.confirmOrders(ID[0]));
		assertEquals(false,publications.confirmOrders(ID[0]));
	}
	
	@Test
	public void test_confirmFulfillment() {
		Mockito.when(confirmedOrders.areFulfilled(ID[0]))
			.thenReturn(true)
				.thenReturn(false);
		Mockito.when(confirmedOrders.findByPublicationId(ID[0]))
			.thenReturn(new ArrayList<ConfirmedOrder>());
		Mockito.when(confirmedOrders.delete(ID[0]))
			.thenReturn(true);
		
		assertEquals(true,publications.confirmFulfillment(ID[0]));
		assertEquals(false,publications.confirmFulfillment(ID[0]));
	}
	
	@Test
	public void test_delete() {
		Mockito.when(publicationDao.delete(ID[0]))
			.thenReturn(true)
				.thenReturn(false);
		
		assertEquals(true,publications.delete(ID[0]));
		assertEquals(false,publications.delete(ID[0]));
	}
	
	@Test
	public void test_setNewSupervisor() {
		Mockito.when(publicationDao.setNewSupervisor(SUPERVISOR[0],ID[0]))
			.thenReturn(true)
				.thenReturn(false);
		
		assertEquals(true,publications.setNewSupervisor(SUPERVISOR[0],ID[0]));
		assertEquals(false,publications.setNewSupervisor(SUPERVISOR[0],ID[0]));
	}
	
	@Test
	public void test_hasSupervisor() {
		Mockito.when(publicationDao.hasSupervisor(ID[0]))
			.thenReturn(true)
				.thenReturn(false);
		
		assertEquals(true,publications.hasSupervisor(ID[0]));
		assertEquals(false,publications.hasSupervisor(ID[0]));
	}
}