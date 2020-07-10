package desapp.grupo.e.service.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.exceptions.AuthenticationCode2FAException;
import org.jboss.aerogear.security.otp.Totp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private UserRepository userRepository;
    private AuthService authService;
    private static final String email = "test@email.test";
    private TotpService totpService;

    @BeforeEach
    public void setUp() {
        totpService = mock(TotpService.class);
        userRepository = mock(UserRepository.class);
        this.authService = new AuthService(userRepository, totpService);
    }
/*
    @Test
    public void createTokenAndUserNotUse2faShouldReturnATokenWithAllProperties() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);

        Assertions.assertNotNull(token.getToken());
        Assertions.assertNotNull(token.getType());
        Assertions.assertFalse(token.getAuthWith2fa());
    }

    @Test
    public void createTokenAndUserUse2faShouldReturnATokenWithOnly2faPropertyInTrue() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().withAuth2fa(true).build());
        TokenDTO token = this.authService.createToken(email);

        Assertions.assertNull(token.getToken());
        Assertions.assertNull(token.getType());
        Assertions.assertTrue(token.getAuthWith2fa());
    }

    @Test
    public void getUsernameByToken() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);

        String username = this.authService.getEmailByToken(token.getType() + " " + token.getToken());
        Assertions.assertEquals(email, username);
    }

    @Test
    public void getUsernameInexistentByTokenShouldThrowJWTVerificationException() {
        String token = "eyJ0eAXiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcmllbEB0ZXN0LnRlc3QiLCJleHAiOjE1OTIyNDU1NDN9.QhyfNhd_DH0sh4GremY7NMsRNOg879orkFMNsfx2mn5Wmc8PiQWbtfWJW5DvBYeGcz4JcdK1QDETQ4TWEzn6lw";
        String tokenString = "Bearer " + token;

        Assertions.assertThrows(JWTVerificationException.class, () -> this.authService.getEmailByToken(tokenString));
    }

    @Test
    public void addTokenToBlackList() {
        String token = "eyJ0eAXiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcmllbEB0ZXN0LnRlc3QiLCJleHAiOjE1OTIyNDU1NDN9.QhyfNhd_DH0sh4GremY7NMsRNOg879orkFMNsfx2mn5Wmc8PiQWbtfWJW5DvBYeGcz4JcdK1QDETQ4TWEzn6lw";
        String tokenString = "Bearer " + token;

        this.authService.addTokenToBlackList(tokenString);

        Assertions.assertTrue(this.authService.isInBlackList(tokenString));
    }

    @Test
    public void getTokenThatIsInBlackListShouldReturnATokenExpiredException() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);

        this.authService.addTokenToBlackList(token.getType() + " " + token.getToken());

        Assertions.assertThrows(TokenExpiredException.class, () -> this.authService.getEmailByToken(token.getType() + " " + token.getToken()));
    }

    @Test
    public void whenUserTryToValidateCode2FAIfThisIsValidThenReturnAToken() {
        User user = UserBuilder.aUser().withEmail(email).withSecret("secretCode").withAuth2fa(true).build();
        when(userRepository.getByEmail(eq(email))).thenReturn(user);
        Totp totp = mock(Totp.class);
        when(totpService.createTotp(anyString())).thenReturn(totp);
        when(totp.verify(anyString())).thenReturn(Boolean.TRUE);

        TokenDTO token = this.authService.createToken(email);
        Assertions.assertNull(token.getToken());

        TokenDTO tokenDTO = this.authService.validateCode2FA(email, "123456");
        Assertions.assertNotNull(tokenDTO.getToken());
    }

    @Test
    public void whenUserTryToValidateCode2faAndCodeIsNotLongShouldThrowAuthenticationCode2FAException() {
        User user = UserBuilder.aUser().withSecret("secretCode").withAuth2fa(true).build();
        when(userRepository.getByEmail(eq(email))).thenReturn(user);
        Totp totp = mock(Totp.class);
        when(totpService.createTotp(anyString())).thenReturn(totp);

        TokenDTO token = this.authService.createToken(email);
        Assertions.assertNull(token.getToken());

        Assertions.assertThrows(AuthenticationCode2FAException.class, () -> {
            this.authService.validateCode2FA(email, "Code_not_long");
        });
    }

    @Test
    public void whenUserTryToValidateCode2faAndCodeIsInvalidShouldThrowAuthenticationCode2FAException() {
        User user = UserBuilder.aUser().withSecret("secretCode").withAuth2fa(true).build();
        when(userRepository.getByEmail(eq(email))).thenReturn(user);
        Totp totp = mock(Totp.class);
        when(totpService.createTotp(anyString())).thenReturn(totp);
        when(totp.verify(anyString())).thenReturn(Boolean.FALSE);

        TokenDTO token = this.authService.createToken(email);
        Assertions.assertNull(token.getToken());

        Assertions.assertThrows(AuthenticationCode2FAException.class, () -> {
            this.authService.validateCode2FA(email, "123456");
        });
    }

    @Test
    public void getSecretCodeOfUserShouldNotThrowException() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);
        String bearerToken = token.getType() + " " + token.getToken();

        Assertions.assertDoesNotThrow(() -> this.authService.getSecret2fa(bearerToken));
        verify(userRepository, times(1)).getSecretKey(eq(email));
    }

    @Test
    public void enabled2faShouldNotThrowException() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);
        String bearerToken = token.getType() + " " + token.getToken();

        Assertions.assertDoesNotThrow(() -> this.authService.enabled2fa(bearerToken));
        verify(userRepository, times(1)).enable2fa(eq(Boolean.TRUE), eq(email));
    }

    @Test
    public void disabled2faShouldNotThrowException() {
        when(userRepository.getByEmail(eq(email))).thenReturn(UserBuilder.aUser().anyUser().build());
        TokenDTO token = this.authService.createToken(email);
        String bearerToken = token.getType() + " " + token.getToken();

        Assertions.assertDoesNotThrow(() -> this.authService.disabled2fa(bearerToken));
        verify(userRepository, times(1)).enable2fa(eq(Boolean.FALSE), eq(email));
    }

 */
}