package servletEmailClient;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * The underlying model for email sessions.
 * 
 * @author Kelsey McKenna
 *
 */
public class Model {
	private Session session;
	private Properties props;
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final int PORT_NO = 587;

	public Model() {
		this.props = getSMTPProperties();
	}

	/**
	 * @return the appropriate SMTP properties for the particular email client,
	 *         port no., etc.
	 */
	private Properties getSMTPProperties() {
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", PORT_NO);

		return props;
	}

	/**
	 * Try to set up an email session for the user with the username and
	 * password
	 * 
	 * @param username
	 *            the username for the email account
	 * @param password
	 *            the password for the email account
	 * @return true if the user can be logged in; false otherwise
	 */
	public boolean setupSession(String username, String password) {
		this.session = Session.getInstance(props);

		try {
			Transport transport = session.getTransport("smtp");
			transport.connect(SMTP_HOST, username, password);
			transport.close();
			return true;
		} catch (MessagingException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	/**
	 * @param from
	 *            the email address which is sending the email
	 * @param to
	 *            a comma-separated list of email addresses to which to send the
	 *            email
	 * @param subject
	 *            the subject for the email
	 * @param content
	 *            the text content of the email
	 * @return a MIME message constructed using the given information
	 * @throws AddressException
	 *             if the given addresses could not be converted
	 * @throws MessagingException
	 *             if the message could not be constructed
	 */
	public MimeMessage constructMimeMessage(String from, String to, String subject, String content)
			throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);

		message.setFrom(new InternetAddress(from));

		// Set the 'to' recipients
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		// Set subject
		message.setSubject(subject);
		// Set the email content
		message.setText(content);
		// Save changes to the message before it is returned
		message.saveChanges();

		return message;
	}

	/**
	 * Send the specified message from the email account for this Sender.
	 * 
	 * Many thanks to Dr Shan He for excerpts of the following code.
	 * 
	 * @param message
	 *            the message to be sent
	 * @param username
	 *            the username for the email account
	 * @param password
	 *            the password for the email account
	 * 
	 * @throws MessagingException
	 *             if the email could not be sent
	 */
	public void sendEmail(MimeMessage message, String username, String password) throws MessagingException {
		Transport tr = session.getTransport("smtp");
		tr.connect(SMTP_HOST, username, password);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
	}

}
