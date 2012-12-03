package Emailer;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail{
	private static final String email = "surprizer637@gmail.com";
	private static final String password = "andrewpaul";
	
	public static void send(String from, String to, String subject, String message) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
 
		Session session = Session.getDefaultInstance(props,
			new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(email, password);
				}
			});
 
		try {
 
			Message emailMessage = new MimeMessage(session);
			emailMessage.setFrom(new InternetAddress(from));
			emailMessage.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			emailMessage.setSubject(subject);
			emailMessage.setContent(message, "text/html");
 
			Transport.send(emailMessage);
 
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}