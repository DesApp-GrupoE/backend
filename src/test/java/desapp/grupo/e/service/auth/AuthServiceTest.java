package desapp.grupo.e.service.auth;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthServiceTest {

    private AuthService authService;
    private static final String email = "test@email.test";

    @BeforeEach
    public void setUp() {
        this.authService = new AuthService();
    }

    @Test
    public void createToken() {
        TokenDTO token = this.authService.createToken(email);

        Assertions.assertNotNull(token);
        Assertions.assertNotNull(token.getToken());
    }

    @Test
    public void getUsernameByToken() {
        TokenDTO token = this.authService.createToken(email);

        String username = this.authService.getUsernameByToken(token.getType() + " " + token.getToken());
        Assertions.assertEquals(email, username);
    }

    @Test
    public void getUsernameInexistentByTokenShouldThrowJWTVerificationException() {
        String token = "eyJ0eAXiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhcmllbEB0ZXN0LnRlc3QiLCJleHAiOjE1OTIyNDU1NDN9.QhyfNhd_DH0sh4GremY7NMsRNOg879orkFMNsfx2mn5Wmc8PiQWbtfWJW5DvBYeGcz4JcdK1QDETQ4TWEzn6lw";
        String tokenString = "Bearer " + token;

        Assertions.assertThrows(JWTVerificationException.class, () -> this.authService.getUsernameByToken(tokenString));
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
        TokenDTO token = this.authService.createToken(email);

        this.authService.addTokenToBlackList(token.getType() + " " + token.getToken());

        Assertions.assertThrows(TokenExpiredException.class, () -> this.authService.getUsernameByToken(token.getType() + " " + token.getToken()));
    }
}