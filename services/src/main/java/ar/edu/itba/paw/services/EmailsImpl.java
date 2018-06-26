package ar.edu.itba.paw.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.EmailService;
import ar.edu.itba.paw.model.Order;
import ar.edu.itba.paw.model.Publication;
import ar.edu.itba.paw.model.User;

@Component
public class EmailsImpl implements EmailService{

	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	@Async
	public void sendEmail(String to, String subject, String text) {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(message, false, "utf-8");
			message.setContent(text, "text/html");
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setFrom("gumpuonline@gmail.com");
			emailSender.send(message);
		} catch (Exception e) {
		}
	}

	@Override
	public void notifyPublicationFulfillment(Publication pub) {
		String publicationDescription = pub.getDescription();
		for(Order order : pub.getOrders()) {
			if(!order.getSubscriber().getUsername().equals(order.getPublication().getSupervisor().getUsername())) {
				sendEmail(order.getSubscriber().getEmail(),
							"[GUMPU] One of your orders needs attention",
							"<h1>News from GUMPU!</h1>" +
							"<p>Your order of "+order.getQuantity()+" "+publicationDescription+" needs attention!</p>" +
							"<p>Contact the supervisor of the publication " +
								"<a href='" + getAbsoluteURL("profile/messaging/"+pub.getId()) + "'>clicking here!</a></p>"
							);
			} else {
				sendEmail(order.getSubscriber().getEmail(),
						"[GUMPU] One of your publications needs attention",
						"<h1>News from GUMPU!</h1>" +
						"<p>Your publication "+publicationDescription+" needs attention!</p>" +
						"<p>Contact the subscribers " +
							"<a href='" + getAbsoluteURL("profile/subscriptions-finalized") + "'>clicking here!</a></p>"
						);
			}
		}
	}
	
	private String getAbsoluteURL(String path) {
		return "http://pawserver.it.itba.edu.ar/paw-2018a-1/"+path;
	}

	@Override
	public void welcomeUser(User user) {
		sendEmail(user.getEmail(),
					"[GUMPU] Thanks for signing in!",
					"<h1>Hi " + user.getUsername() + "!</h1><h3>GUMPU Thanks you for signing in!</h3>" +
					"<p>You might start...</p>" +
					"<ul>" +
						"<li>" +
							"<a href='" + getAbsoluteURL("profile/publications?newModal=true") + "'>Creating publications</a>" +
						"</li>" +
						"<li>" +
							"<a href='" + getAbsoluteURL("search") + "'>Subscribing to existing publications</a>" +
						"</li>" +
					"</ul>"
					);
	}

}
