package ar.edu.itba.paw.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.services.UsersImpl;

public class UsersImplTest {
	private static final String PASSWORD = "pass";
	private static final int [] ID = {1,2};
	private static final String [] USERNAME = {"user1","user2"};
	private static final String [] EMAIL = {"user1@example.com","user2@example.com"};
	private static final String USERNAME_UNKNOWN = "userx";
	private static final String EMAIL_UNKNOWN = "userx@example.com";

	@Mock
	private UserDao userDao;

	@InjectMocks
	private UsersImpl users;
	
	@Before
	public void setUp() {
		users = new UsersImpl();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void test_findByUsername() {
		Mockito.when(userDao.findByUsername(USERNAME[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD)));
		Mockito.when(userDao.findByUsername(USERNAME_UNKNOWN))
			.thenReturn(Optional.empty());
		
		Optional<User> user = users.findByUsername(USERNAME[0]);
		assertTrue(user.isPresent());
		assertEquals(ID[0],user.get().getId());
		assertEquals(USERNAME[0],user.get().getUsername());
		assertEquals(EMAIL[0],user.get().getEmail());
		assertEquals(PASSWORD,user.get().getPassword());
		
		user = users.findByUsername(USERNAME_UNKNOWN);
		assertFalse(user.isPresent());
	}
	
	@Test
	public void test_findByEmail() {
		Mockito.when(userDao.findByEmail(EMAIL[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD)));
		Mockito.when(userDao.findByEmail(EMAIL_UNKNOWN))
			.thenReturn(Optional.empty());
		
		Optional<User> user = users.findByEmail(EMAIL[0]);
		assertTrue(user.isPresent());
		assertEquals(ID[0],user.get().getId());
		assertEquals(USERNAME[0],user.get().getUsername());
		assertEquals(EMAIL[0],user.get().getEmail());
		assertEquals(PASSWORD,user.get().getPassword());
		
		user = users.findByEmail(EMAIL_UNKNOWN);
		assertFalse(user.isPresent());
	}
	
	@Test
	public void test_findById() {
		Mockito.when(userDao.findById(ID[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD)));
		
		Optional<User> user = users.findById(ID[0]);
		assertTrue(user.isPresent());
		assertEquals(ID[0],user.get().getId());
		assertEquals(USERNAME[0],user.get().getUsername());
		assertEquals(EMAIL[0],user.get().getEmail());
		assertEquals(PASSWORD,user.get().getPassword());
	}
	
	@Test
	public void test_transaction() {
		int transactionsBefore = 0, transactionsAfter = 1;
		Mockito.when(userDao.findByUsername(USERNAME[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD,transactionsBefore)))
				.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD,transactionsAfter)));
		Mockito.when(userDao.addTransaction(USERNAME[0]))
			.thenReturn(true);
		
		assertEquals(transactionsBefore,users.transaction(USERNAME[0]));
		assertTrue(users.addTransaction(USERNAME[0]));
		assertEquals(transactionsAfter,users.transaction(USERNAME[0]));
	}
	
	@Test
	public void test_uniqueUser() {
		Mockito.when(userDao.findByUsername(USERNAME[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD)));
		Mockito.when(userDao.findByUsername(USERNAME[1]))
			.thenReturn(Optional.empty());

		assertFalse(users.uniqueUser(USERNAME[0]));
		assertTrue(users.uniqueUser(USERNAME[1]));
	}
	
	@Test
	public void test_uniqueEmail() {
		Mockito.when(userDao.findByEmail(EMAIL[0]))
			.thenReturn(Optional.of(new User(ID[0],USERNAME[0],EMAIL[0],PASSWORD)));
		Mockito.when(userDao.findByEmail(EMAIL[1]))
			.thenReturn(Optional.empty());

		assertFalse(users.uniqueEmail(EMAIL[0]));
		assertTrue(users.uniqueEmail(EMAIL[1]));
	}
}