package desapp.grupo.e.service.mail;

import desapp.grupo.e.service.utils.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private MailSender mailSender;
    private static final String WELCOME_SUBJECT = "Bienvenido a Desapp Grupo E";
    private static final String WELCOME_MESSAGE = "<h2><a href=\"https://pacific-cove-71520.herokuapp.com/\" _target=\"blank\">Bienvenido %s a Desapp Grupo E</a></h2>";

    public MailService() {
        this.mailSender = new MailSender();
    }

    @Async
    public void sendWelcomeEmail(String to, String fullName) {
        this.mailSender.sendEmailHtml(to, WELCOME_SUBJECT, String.format(WELCOME_MESSAGE, fullName));
    }
}
