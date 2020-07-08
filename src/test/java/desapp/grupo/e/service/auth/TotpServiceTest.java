package desapp.grupo.e.service.auth;

import org.jboss.aerogear.security.otp.Totp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TotpServiceTest {

    private TotpService totpService;

    @BeforeEach
    public void setUp() {
        this.totpService = new TotpService();
    }

    @Test
    public void createTopt() {
        Totp totp = this.totpService.createTotp("secretCode");

        Assertions.assertNotNull(totp);
    }
}