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

import ar.edu.itba.paw.interfaces.ConfirmedOrderDao;
import ar.edu.itba.paw.interfaces.OrderDao;
import ar.edu.itba.paw.model.ConfirmedOrder;
import ar.edu.itba.paw.model.Order;

public class ConfirmedOrdersImplTest {
	private static final int ID = 1;
	private static final String USERNAME = "user";
	private static final int QUANTITY = 10;

	@Mock
	private ConfirmedOrderDao orderDao;
	
	@InjectMocks
	private ConfirmedOrdersImpl orders;
	
	@Before
	public void setUp() {
		orders = new ConfirmedOrdersImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_findBySubscriber() {
		List<ConfirmedOrder> mockList = new ArrayList<ConfirmedOrder>();
		mockList.add(new ConfirmedOrder(ID,USERNAME,QUANTITY,false,false));
		
		Mockito.when(orderDao.findByBuyer(USERNAME))
			.thenReturn(mockList);
		
		List<ConfirmedOrder> ord = orders.findByBuyer(USERNAME);
		assertFalse(ord.isEmpty());
		assertEquals(ID,ord.get(0).getPublication_id());
		assertEquals(USERNAME,ord.get(0).getBuyer());
		assertEquals(QUANTITY,ord.get(0).getQuantity());
		assertEquals(false,ord.get(0).getPaid());
		assertEquals(false,ord.get(0).getReceived());
	}
	
	@Test
	public void test_findByPublicationId() {
		List<ConfirmedOrder> mockList = new ArrayList<ConfirmedOrder>();
		mockList.add(new ConfirmedOrder(ID,USERNAME,QUANTITY,false,false));
		
		Mockito.when(orderDao.findByPublicationId(ID))
			.thenReturn(mockList);
		
		List<ConfirmedOrder> ord = orders.findByPublicationId(ID);
		assertFalse(ord.isEmpty());
		assertEquals(ID,ord.get(0).getPublication_id());
		assertEquals(USERNAME,ord.get(0).getBuyer());
		assertEquals(QUANTITY,ord.get(0).getQuantity());
		assertEquals(false,ord.get(0).getPaid());
		assertEquals(false,ord.get(0).getReceived());
	}
	
	@Test
	public void test_create() {
		Mockito.when(orderDao.create(ID,USERNAME,QUANTITY))
			.thenReturn(new ConfirmedOrder(ID,USERNAME,QUANTITY));
		
		ConfirmedOrder ord = orders.create(ID,USERNAME,QUANTITY);
		assertNotNull(ord);
		assertEquals(ID,ord.getPublication_id());
		assertEquals(USERNAME,ord.getBuyer());
		assertEquals(QUANTITY,ord.getQuantity());
	}
	
	@Test
	public void test_confirmPayment() {
		Mockito.when(orderDao.confirmPayment(ID,USERNAME))
		.thenReturn(true)
			.thenReturn(false);
	
		assertEquals(true,orders.confirmPayment(ID,USERNAME));
		assertEquals(false,orders.confirmPayment(ID,USERNAME));
	}
	
	@Test
	public void test_confirmDelivery() {
		Mockito.when(orderDao.confirmDelivery(ID,USERNAME))
		.thenReturn(true)
			.thenReturn(false);
	
		assertEquals(true,orders.confirmDelivery(ID,USERNAME));
		assertEquals(false,orders.confirmDelivery(ID,USERNAME));
	}
	
	@Test
	public void test_delete() {
		Mockito.when(orderDao.delete(ID))
		.thenReturn(true)
			.thenReturn(false);
	
		assertEquals(true,orders.delete(ID));
		assertEquals(false,orders.delete(ID));
	}
}