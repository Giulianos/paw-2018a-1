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
	 * Finds the user with the provided username.
	 * 
	 * @param username The username of the user
	 * @return The user with the provided username.
	 */
	
	public User findByUsername(final String username);
	
	/**
	 * Finds the user with the provided email.
	 * 
	 * @param email The Email of the user
	 * @return The user with the provided email.
	 */
	
	public User findByEmail(final String email);
	
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

	/**
	 * Check if the user is valid.
	 * 
	 * @param username user to be checked.
	 * 
	 * @return True if the user is unique.
	 */
	public boolean uniqueUser(final String username);

	/**
	 * Check if the email is valid.
	 * 
	 * @param email email to be checked.
	 * 
	 * @return True if the email is unique.
	 */
	public boolean uniqueEmail(final String email);
}