package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface EmailService {
	/**
	 * Send welcome email to user
	 * @param user User to welcome
	 */
	public void welcomeUser(User user);

	/**
	 * Send publication fulfillment notification to orderer
	 *
	 * @param user User to notify
	 * @param publication Fulfilled publication
	 */
	public void notifyOrdererPublicationFulfillment(final User user, final Publication publication);


	/**
	 * Send publication fulfillment notification to supervisor
	 * @param publication
	 */
	public void notifySupervisorPublicationFulfillment(final Publication publication);
}
