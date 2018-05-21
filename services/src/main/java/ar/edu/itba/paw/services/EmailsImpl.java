package ar.edu.itba.paw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.Emails;

@Component
public class EmailsImpl implements Emails{

	@Autowired
	public JavaMailSender emailSender;
	
	@Override
	public void sendEmail(String to, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage(); 
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
	}

}