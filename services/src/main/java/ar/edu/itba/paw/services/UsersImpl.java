package ar.edu.itba.paw.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.UserService;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class UsersImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Override
	public Optional<User> findById(final long id) {
		return userDao.findById(id);
	}
	
	@Override
	public Optional<User> findByUsername(final String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		return userDao.findByEmail(email);
	}
	
	@Override
	public User create(final String username, final String email, final String password) {
		return userDao.create(username, email, passEncoder.encode(password));
	}

	@Override
	public int transaction(String username) {
		Optional<User> user = userDao.findByUsername(username);
		return user.isPresent() ? user.get().getTransactions() : 0;
	}

	@Override
	public boolean addTransaction(String username) {
		return userDao.addTransaction(username);
	}

	public boolean uniqueUser(final String username) {
		return !findByUsername(username).isPresent();
	}

	public boolean uniqueEmail(final String email) {
		return !findByEmail(email).isPresent();
	}
}