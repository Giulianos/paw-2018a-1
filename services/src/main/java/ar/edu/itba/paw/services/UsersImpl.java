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
	public User findById(Integer id) {
		if(id == null) {
			return null;
		}
		if(id == 1){
			return new User("user1", 1);
		}
		if(id == 2){
			return new User("user2", 2);
		}
		return null;
	}

}