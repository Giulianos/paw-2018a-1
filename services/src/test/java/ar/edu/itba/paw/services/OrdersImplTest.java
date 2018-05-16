package ar.edu.itba.paw.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.model.Order;

public class OrdersImplTest {
	private static final int ID = 1;
	private static final String USERNAME = "user";
	private static final int QUANTITY = 10;

	@Mock
	private OrderDao orderDao;
	
	@InjectMocks
	private OrdersImpl orders;
	
	@Before
	public void setUp() {
		orders = new OrdersImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_findBySubscriber() {
		List<Order> mockList = new ArrayList<Order>();
		mockList.add(new Order(ID,USERNAME,QUANTITY,false));
		
		Mockito.when(orderDao.findBySubscriber(USERNAME))
			.thenReturn(mockList);
		
		List<Order> ord = orders.findBySubscriber(USERNAME);
		assertFalse(ord.isEmpty());
		assertEquals(ID,ord.get(0).getPublication_id());
		assertEquals(USERNAME,ord.get(0).getSubscriber());
		assertEquals(QUANTITY,ord.get(0).getQuantity());
		assertEquals(false,ord.get(0).getConfirmed());
	}
	
	@Test
	public void test_findByPublicationId() {
		List<Order> mockList = new ArrayList<Order>();
		mockList.add(new Order(ID,USERNAME,QUANTITY,false));
		
		Mockito.when(orderDao.findByPublicationId(ID))
			.thenReturn(mockList);
		
		List<Order> ord = orders.findByPublicationId(ID);
		assertFalse(ord.isEmpty());
		assertEquals(ID,ord.get(0).getPublication_id());
		assertEquals(USERNAME,ord.get(0).getSubscriber());
		assertEquals(QUANTITY,ord.get(0).getQuantity());
		assertEquals(false,ord.get(0).getConfirmed());
	}
	
	@Test
	public void test_create() {
		Mockito.when(orderDao.create(ID,USERNAME,QUANTITY))
			.thenReturn(new Order(ID,USERNAME,QUANTITY));
		
		Order ord = orders.create(ID,USERNAME,QUANTITY);
		assertNotNull(ord);
		assertEquals(ID,ord.getPublication_id());
		assertEquals(USERNAME,ord.getSubscriber());
		assertEquals(QUANTITY,ord.getQuantity());
	}
	
	@Test
	public void test_confirm() {
		Mockito.when(orderDao.confirm(ID,USERNAME))
		.thenReturn(true)
			.thenReturn(false);
	
		assertEquals(true,orders.confirm(ID,USERNAME));
		assertEquals(false,orders.confirm(ID,USERNAME));
	}
	
	@Test
	public void test_delete() {
		Mockito.when(orderDao.delete(ID,USERNAME))
		.thenReturn(true)
			.thenReturn(false);
	
		assertEquals(true,orders.delete(ID,USERNAME));
		assertEquals(false,orders.delete(ID,USERNAME));
	}
}