package allClasses;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

	private static final String USER_NAME = "car.park.system.se"; // GMail user name (part before "@gmail.com")
	private static final String PASSWORD = "cps123456";
	private static final String sendMailHost = "smtp.gmail.com";
	private static final String CheckMailHost = "pop.gmail.com";
	
	public static void sendEmail(String to, String subject, String body) {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", sendMailHost);
		props.put("mail.smtp.user", USER_NAME);
		props.put("mail.smtp.password", PASSWORD);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress("car.park.system.se@gmail.com", "Car Park System"));
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(Message.RecipientType.TO, toAddress);
			message.setSubject(subject);
			message.setText(body);
			Transport transport = session.getTransport("smtp");
			transport.connect(sendMailHost, USER_NAME, PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ae) {
			ae.printStackTrace();
		} catch (MessagingException me) {
			me.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static boolean checkAndDeleteMail(String sender) {
		try {
			Properties properties = new Properties();
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.pop3s.host", CheckMailHost);
			properties.put("mail.pop3s.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			Session emailSession = Session.getDefaultInstance(properties);
			
			Store store = emailSession.getStore("pop3s");
			store.connect(CheckMailHost, USER_NAME, PASSWORD);
			
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);
			
			Message[] messages = emailFolder.getMessages();
//			System.out.println("messages.length---" + messages.length);
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				String from = message.getFrom()[0].toString();
//				String subject = message.getSubject();
				if (from.contains(sender)) {
					// set the DELETE flag to true
					message.setFlag(Flags.Flag.DELETED, true);
//					System.out.println("Marked DELETE for message: " + subject);
					emailFolder.close(true);
					store.close();
					return true;
				}
			}
			emailFolder.close(true);
			store.close();
			return false;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
			return false;
		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
	}

}
