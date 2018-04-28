package ar.edu.itba.paw.persistence;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.model.User;

@Primary
@Repository
public class UserJDBCDao implements UserDao {

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
