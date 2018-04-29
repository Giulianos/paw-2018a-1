package ar.edu.itba.paw.services;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.interfaces.Users;
import ar.edu.itba.paw.model.User;

@Service
public class AnotherUsersImpl implements Users {

	@Override
	public User findById(Integer id) {
		if(id == null) {
			return null;
		}
		if(id == 1){
			return new User("another user1", 1);
		}
		if(id == 2){
			return new User("another user2", 2);
		}
		return null;
	}

}