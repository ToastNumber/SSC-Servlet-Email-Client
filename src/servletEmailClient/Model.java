package servletEmailClient;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Model {
	private Session session;
	private Properties props;
	private static final String SMTP_HOST = "smtp.gmail.com";
	private static final int PORT_NO = 587;

	public Model() {
		this.props = getSMTPProperties();
	}

	private Properties getSMTPProperties() {
		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", SMTP_HOST);
		props.put("mail.smtp.port", PORT_NO);

		return props;
	}

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
	 * @param session
	 * @param from
	 *            the email address which is sending the email
	 * @param to
	 *            a comma-separated list of email addresses to which to send the
	 *            email
	 * @param cc
	 *            a comma-separated list of email addresses for the cc field.
	 * @param subject
	 *            the subject for the email
	 * @param content
	 *            the text content of the email
	 * @param attachments
	 *            the attachments for the email
	 * @return a MIME message constructed using the given information
	 * @throws AddressException
	 * @throws MessagingException
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
	 * @throws MessagingException
	 */
	public void sendEmail(MimeMessage message, String username, String password) throws MessagingException {
		Transport tr = session.getTransport("smtp");
		tr.connect(SMTP_HOST, username, password);
		tr.sendMessage(message, message.getAllRecipients());
		tr.close();
	}

	
}
