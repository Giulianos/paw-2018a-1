package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.User;

public interface Users {
	
	/**
	 * Finds the user with the provided id.
	 * 
	 * @param id The id of the user
	 * @return The user with the provided id.
	 */
	
	public User findById(final long id);
	
	
	/**
	 * Create a new user.
	 * 
	 * @param username The name of the user.
	 * @param email The email associated with the user.
	 * @param password The password of the user account.
	 * 
	 * @return The created user.
	 */
	User create(String username, String email, String password);
}