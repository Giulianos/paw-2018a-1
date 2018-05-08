package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface UserDao {

	/**
	 * Finds the user with the provided id.
	 * 
	 * @param id The id of the user.
	 * 
	 * @return The user with the provided id.
	 */
	
	User findById(final long id);
	
	/**
	 * Finds the user with the provided username.
	 * 
	 * @param username The username of the user.
	 * 
	 * @return The user with the provided username.
	 */
	
	User findByUsername(final String username);
	
	/**
	 * Finds the user with the provided email.
	 * 
	 * @param email The Email of the user.
	 * 
	 * @return The user with the provided email.
	 */
	
	User findByEmail(final String email);
	
	/**
	 * Creates a new user.
	 * 
	 * @param username The name of the user.
	 * @param email The email associated with the user.
	 * @param password The password of the user account.
	 * 
	 * @return The created user.
	 */
	
	User create(final String username, final String email, final String password);
}