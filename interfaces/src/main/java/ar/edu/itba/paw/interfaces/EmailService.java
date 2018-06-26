package ar.edu.itba.paw.interfaces;

import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

public interface EmailService {
	
	/**
	 * Sends an email
	 * 
	 * @param to The recipient of the email
	 * @param subject The subject of the email
	 * @param text The email's content
	 */
	
	public void sendEmail(final String to, final String subject, final String text); 
	
	
	/**
	 * Send welcome email to user
	 * @param user User to welcome
	 */
	public void welcomeUser(User user);
	
	
	/**
	 * Send email notifying the fulfillment of a publication
	 * @param pub Publication that has been fulfilled
	 */
	public void notifyPublicationFulfillment(Publication pub);
}
