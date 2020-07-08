package desapp.grupo.e.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.service.exceptions.AuthenticationCode2FAException;
import desapp.grupo.e.webservice.security.SecurityConstants;
import desapp.grupo.e.persistence.user.UserRepository;
import org.jboss.aerogear.security.otp.Totp;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthService {

    public List<String> blackListToken;
    public UserRepository userRepository;
    public TotpService toptService;
    public Map<String, TokenDTO> emailToken;

    public AuthService(UserRepository userRepository, TotpService totpService) {
        this.userRepository = userRepository;
        this.blackListToken = new ArrayList<>();
        this.emailToken = new HashMap<>();
        this.toptService = totpService;
    }

    public TokenDTO createToken(String emailUser) {
        User user = userRepository.getByEmail(emailUser);

        LocalDateTime expiresIn = LocalDateTime.now().plusHours(1);
        String stringToken = JWT.create()
                .withSubject(emailUser)
                .withExpiresAt(Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setType(SecurityConstants.TOKEN_PREFIX);
        tokenDTO.setToken(stringToken);
        tokenDTO.setExpiresIn(expiresIn.atZone(ZoneId.systemDefault()).toEpochSecond());

        if (!user.getAuth2fa()) {
            return tokenDTO;
        } else {
            this.emailToken.put(user.getEmail(), tokenDTO);
            return new TokenDTO(true);
        }
    }

    public String getEmailByToken(String token) {
        if(blackListToken.contains(token)) {
            throw new TokenExpiredException("Token expired");
        }
        return JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()))
                .build()
                .verify(token.replace(SecurityConstants.TOKEN_PREFIX + " ", ""))
                .getSubject();
    }

    public TokenDTO validateCode2FA(String email, String code) {
        User user = userRepository.getByEmail(email);
        Totp totp = this.toptService.createTotp(user.getSecret());
        if (!isValidLong(code) || !totp.verify(code)) {
            throw new AuthenticationCode2FAException(String.format("Code '%s' not valid", code));
        }
        return this.emailToken.remove(email);
    }

    private boolean isValidLong(String code) {
        try {
            Long.parseLong(code);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void addTokenToBlackList(String token) {
        this.blackListToken.add(token);
    }

    public Boolean isInBlackList(String token) {
        return this.blackListToken.contains(token);
    }

    @Transactional
    public void enabled2fa(String token) {
        String email = getEmailByToken(token);
        userRepository.enable2fa(true, email);
    }

    @Transactional
    public void disabled2fa(String token) {
        String email = getEmailByToken(token);
        userRepository.enable2fa(false, email);
    }

    @Transactional(readOnly = true)
    public String getSecret2fa(String token) {
        String email = getEmailByToken(token);
        return userRepository.getSecretKey(email);
    }
}
