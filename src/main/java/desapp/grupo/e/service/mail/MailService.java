package desapp.grupo.e.service.mail;

import desapp.grupo.e.service.utils.MailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private MailSender mailSender;
    private static final String WELCOME_SUBJECT = "[Desapp Grupo E] Bienvenido";
    private static final String WELCOME_MESSAGE = "<h2><a href=\"https://pacific-cove-71520.herokuapp.com/\" _target=\"blank\">Bienvenido %s a Desapp Grupo E</a></h2>";
    private static final String PURCHASE_TURN_SUBJECT = "[Desapp Grupo E] Turno de compra #%s";
    private static final String PURCHASE_TURN_MESSAGE = "El horario del turno de la compra #%s esta por cumplirse. Recuerda ir al comercio donde realizaste la compra";

    public MailService() {
        this.mailSender = new MailSender();
    }

    @Async
    public void sendWelcomeEmail(String to, String fullName) {
        this.mailSender.sendEmailHtml(to, WELCOME_SUBJECT, String.format(WELCOME_MESSAGE, fullName));
    }

    @Async
    public void sendPurchaseTurnEmail(String to, Long idPurchase) {
        this.mailSender.sendEmailHtml(to,
                String.format(PURCHASE_TURN_SUBJECT, idPurchase),
                String.format(PURCHASE_TURN_MESSAGE, idPurchase));
    }
}
