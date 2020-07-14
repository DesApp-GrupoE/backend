package desapp.grupo.e.service.utils;

import desapp.grupo.e.service.log.Log;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailSender {

    private static final String USERNAME_EMAIL_APP = "4716a978c74a5e";

    public void sendEmailHtml(String to, String subject, String msg) {
        Session session = createSession();
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME_EMAIL_APP));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setContent(msg, "text/html");
            Transport.send(message);
            Log.info(String.format("Email to: '%s' - Subject: '%s' - Sended", to, subject));
        } catch (MessagingException e) {
            Log.error(String.format("Email to: '%s' - Subject: '%s' - Not sended", to, subject));
            Log.exception(e);
        }
    }

    private Session createSession() {
        final String password = "9590a3426aae67";

        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.mailtrap.io");
        prop.put("mail.smtp.port", "2525");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME_EMAIL_APP, password);
                    }
                });
    }


}
