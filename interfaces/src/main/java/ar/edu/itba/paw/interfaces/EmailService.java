package ar.edu.itba.paw.interfaces;

public interface EmailService {
	
	/**
	 * Sends an email
	 * 
	 * @param to The recipient of the email
	 * @param subject The subject of the email
	 * @param text The email's content
	 */
	
	public void sendEmail(final String to, final String subject, final String text); 
}
