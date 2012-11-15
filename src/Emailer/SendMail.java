/*
 * THIS CODE CAME FROM http://www.javabeat.net/2007/10/sending-mail-from-java/
 * 
 */
package Emailer;
 
import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMail {
 
    private String from;
    private String to;
    private String subject;
    private String text;
 
    public SendMail(String from, String to, String subject, String text){
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
    }
 
    public void send(){
		final String username = "suprizer637@gmail.com";
	    final String password = "suprizer";

	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", "smtp.gmail.com");
	    props.put("mail.smtp.port", "587");

	    Session session = Session.getInstance(props,
	      new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
	      });

	    try {

	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(this.from));
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(this.to));
	        message.setSubject(this.subject);
	        message.setText(this.text);
	        Transport.send(message);

	        System.out.println("Done");

	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }
	}
}