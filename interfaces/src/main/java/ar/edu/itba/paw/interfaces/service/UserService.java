package ar.edu.itba.paw.interfaces.service;

import java.util.Optional;

import ar.edu.itba.paw.model.User;

public interface UserService {
	
	/**
	 * Finds the user with the provided id.
	 * 
	 * @param id The id of the user.
	 * 
	 * @return The user with the provided id.
	 */
	
	public Optional<User> findById(final long id);
	
	/**
	 * Finds the user with the provided username.
	 * 
	 * @param username The username of the user.
	 * 
	 * @return The user with the provided username.
	 */
	
	public Optional<User> findByUsername(final String username);
	
	/**
	 * Finds the user with the provided email.
	 * 
	 * @param email The Email of the user.
	 * 
	 * @return The user with the provided email.
	 */
	
	public Optional<User> findByEmail(final String email);
	
	/**
	 * Create a new user.
	 * 
	 * @param username The name of the user.
	 * @param email The email associated with the user.
	 * @param password The password of the user account.
	 * 
	 * @return The created user.
	 */
	
	public User create(String username, String email, String password);

	/**
	 * Checks if there already exists a user with that username or email.
	 * 
	 * @param username The to be checked.
	 * 
	 * @param email The email to be checked
	 * 
	 * @return True if the user is already exists.
	 */
	public boolean userExists(final String username, final String email);
	
	/**
	 * Updates the reputation of the provided user.
	 * 
	 * @param username The username of the user to be updated.
	 * 
	 * @param reputation The new reputation.
	 */
	public void updateReputation(String username, Integer reputation);
	
	/**
	 * Gets the reputation of the provided user.
	 * 
	 * @param user Username of the user to look for the reputation.
	 * 
	 * @return the value of the reputation. null is returned if was never qualified.
	 */
	public Integer getReputation(String username);
}