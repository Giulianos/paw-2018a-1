package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class UsersImpl implements Users {
	@Autowired
	private UserDao userDao;
	
	@Override
	public User findById(final long id) {
		return userDao.findById(id);
	}
	
	@Override
	public User findByUsername(final String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findByEmail(final String email) {
		return userDao.findByEmail(email);
	}
	
	@Override
	public User create(final String username, final String email, final String password) {
		return userDao.create(username, email, password);
	}
}