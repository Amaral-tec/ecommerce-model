package br.com.amaral.service;

/**
 * Library https://javaee.github.io/javamail/docs/api/com/sun/mail/smtp/package-summary.html
 */

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ServiceSendEmail {
	
	private String userName = "amaraljje@gmail.com";
	private String password = "Educ131919$";
	
	@Async
	public void sendEmailHtml(String subject, String body, String recipient) throws UnsupportedEncodingException, MessagingException {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.ssl.trust", "*");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls", "false");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		Session session = Session.getInstance(properties, new Authenticator() {
		      
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				
				return new PasswordAuthentication(userName, password);
			}
		});
		
		session.setDebug(true);
		
		Address[] toUser = InternetAddress.parse(recipient);
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(userName, "Messaging Test", "UTF-8"));
		message.setRecipients(Message.RecipientType.TO, toUser);
		message.setSubject(subject);
		message.setContent(body, "text/html; charset=utf-8");
		
		Transport.send(message);
	}

}


