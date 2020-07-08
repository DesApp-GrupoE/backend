package desapp.grupo.e.service.auth;

import org.jboss.aerogear.security.otp.Totp;
import org.springframework.stereotype.Service;

@Service
public class TotpService {

    public Totp createTotp(String secretCode) {
        return new Totp(secretCode);
    }
}
