package ar.edu.itba.paw.interfaces.dao;

import java.util.Optional;

import ar.edu.itba.paw.model.User;

public interface UserDao {

	/**
	 * Finds the user with the provided id.
	 * 
	 * @param id The id of the user.
	 * 
	 * @return The user with the provided id.
	 */
	
	public Optional<User> findById(final long id);
	
	/**
	 * Finds the user with the provided email.
	 * 
	 * @param email The Email of the user.
	 * 
	 * @return The user with the provided email.
	 */
	
	public Optional<User> findByEmail(final String email);
	
	/**
	 * Creates a new user.
	 * 
	 * @param name The name of the user.
	 * @param email The email associated with the user.
	 * @param password The password of the user account.
	 * 
	 * @return The created user.
	 */
	
	public User create(final String name, final String email, final String password);
	
	/**
	 * Updates the user object.
	 * 
	 * @param user The user to be updated.
	 * 
	 * @return true if the update was successful.
	 */
	public boolean updateUser(User user);
	
}