package ar.edu.itba.paw.interfaces;

import java.util.Optional;

import java.util.List;

import ar.edu.itba.paw.model.User;

public interface UserDao {

	/**
	 * Finds the user with the provided id.
	 * 
	 * @param id The id of the user.
	 * 
	 * @return The user with the provided id.
	 */
	
	Optional<User> findById(final long id);
	
	/**
	 * Finds the user with the provided username.
	 * 
	 * @param username The username of the user.
	 * 
	 * @return The user with the provided username.
	 */
	
	Optional<User> findByUsername(final String username);
	
	/**
	 * Finds the user with the provided email.
	 * 
	 * @param email The Email of the user.
	 * 
	 * @return The user with the provided email.
	 */
	
	Optional<User> findByEmail(final String email);
	
	/**
	 * Return users subscribed to a provided publication
	 * 
	 * @param publication_id The id of the publication to get the subscribers from
	 * 
	 * @return A list with the subscribers
	 */
	
	List<User> getSubscribersOfPublication(final long publications_id);
	
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
	
	/**
	 * Increase the transactions attribute for the provided user by 1.
	 * 
	 * @param username The name of the user.
	 * 
	 * @return True if it the count update was successful.
	 */
	
	boolean addTransaction(final String username);
}