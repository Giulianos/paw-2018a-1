package ar.edu.itba.paw.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.itba.paw.interfaces.dao.UserDao;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;

@Primary
@Service
public class UserServiceImpl implements UserService {
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
	public boolean userExists(final String username, final String email) {
		return findByUsername(username).isPresent() || findByEmail(email).isPresent();
	}

	@Override
	@Transactional
	public void updateReputation(String username, Integer reputation) {
		System.out.println("--------------------->Updated reputation of: "+username);
		System.out.println("--------------------->Qualified with: "+reputation);
		User user = userDao.findByUsername(username).get();
		user.setNumberOfQualifications(user.getNumberOfQualifications()+1);
		if(user.getReputation() == null) {
			user.setReputation(reputation);
		} else {
			user.setReputation((reputation + user.getReputation()) / user.getNumberOfQualifications());
		}
		userDao.updateUser(user);
	}

	@Override
	public Integer getReputation(String username) {
		User user = userDao.findByUsername(username).get();
		return user.getReputation();
	}
}