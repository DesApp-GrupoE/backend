package desapp.grupo.e.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.webservice.security.SecurityConstants;
import org.springframework.stereotype.Service;
import desapp.grupo.e.persistence.user.UserRepository;
import desapp.grupo.e.service.utils.RandomString;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthService {

    public List<String> blackListToken;
    public UserRepository userRepository;
    public Map<String, TokenDTO> emailToken;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.blackListToken = new ArrayList<>();
        this.emailToken = new HashMap<>();
    }

    public TokenDTO createToken(String emailUser) {

        Optional<User> optionalUser = userRepository.findByEmail(emailUser);
        User user = optionalUser.get();

        LocalDateTime expiresIn = LocalDateTime.now().plusHours(1);
        String stringToken = JWT.create()
                .withSubject(emailUser)
                .withExpiresAt(Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        TokenDTO tokenDTO = new TokenDTO();
        this.emailToken.put(user.getEmail(), tokenDTO);
        tokenDTO.setType(SecurityConstants.TOKEN_PREFIX);
        tokenDTO.setToken(stringToken);
        tokenDTO.setExpiresIn(expiresIn.atZone(ZoneId.systemDefault()).toEpochSecond());

        if (!user.getAuth2fa()) {
            return tokenDTO;
        }    
        else {
            TokenDTO tokenWith2FA = new TokenDTO();
            RandomString randomString = new RandomString();
            String secretKey = randomString.nextString(15);
            tokenWith2FA.setSecretKey(secretKey);
            user.setSecret(secretKey);
            userRepository.save(user);
            return tokenWith2FA;
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

    public void addTokenToBlackList(String token) {
        this.blackListToken.add(token);
    }

    public Boolean isInBlackList(String token) {
        return this.blackListToken.contains(token);
    }

    public TokenDTO getTokenByEmail(String email) {
        return this.emailToken.get(email);
    }
}
