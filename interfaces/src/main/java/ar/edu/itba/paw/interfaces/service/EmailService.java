package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.User;

public interface EmailService {
	/**
	 * Send welcome email to user
	 * @param user User to welcome
	 */
	public void welcomeUser(User user);
}
