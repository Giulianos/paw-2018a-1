package ar.edu.itba.paw.services;

import java.util.Optional;

import ar.edu.itba.paw.interfaces.service.EmailService;
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

	@Autowired
	private EmailService emailService;
	
	@Override
	public Optional<User> findById(final long id) {
		return userDao.findById(id);
	}

	@Override
	public Optional<User> findByEmail(final String email) {
		return userDao.findByEmail(email);
	}
	
	@Override
	public User create(final String name, final String email, final String password) {
		User createdUser = userDao.create(name, email, passEncoder.encode(password));
		if(createdUser != null) {
			emailService.welcomeUser(createdUser);
		}

		return createdUser;
	}

	@Override
	public boolean userExists(final String email) {
		return findByEmail(email).isPresent();
	}
}