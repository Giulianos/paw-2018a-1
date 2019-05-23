package ar.edu.itba.paw.services;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import ar.edu.itba.paw.interfaces.service.EmailService;
import ar.edu.itba.paw.model.User;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Component
public class EmailsImpl implements EmailService {

    private final int THREADS_QUANTITY = 10;

    @Autowired
    private JavaMailSender emailSender;

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(THREADS_QUANTITY);

    @Async
    public void sendEmail(String to, String subject, String text) {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, false, "utf-8");
            message.setContent(text, "text/html");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("Gumpu <gumpuonline@gmail.com>");
            emailSender.send(message);
        } catch (Exception e) {
        }
    }

    private String getAbsoluteURL(String path) {
        return "http://pawserver.it.itba.edu.ar/paw-2018a-1/" + path;
    }

    @Override
    public void welcomeUser(User user) {
        executorService.submit(() -> {
            sendEmail(user.getEmail(),
                    "\uD83C\uDF89 Thanks for signing in!",
                    "<h1>Hi " + user.getName() + "!</h1><h3>GUMPU Thanks you for signing in!</h3>" +
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
        });
    }

}
