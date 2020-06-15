package desapp.grupo.e.service.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.webservice.security.SecurityConstants;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class AuthService {

    public List<String> blackListToken;

    public AuthService() {
        this.blackListToken = new ArrayList<>();
    }

    public TokenDTO createToken(String emailUser) {
        LocalDateTime expiresIn = LocalDateTime.now().plusHours(1);
        String stringToken = JWT.create()
                .withSubject(emailUser)
                .withExpiresAt(Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setType(SecurityConstants.TOKEN_PREFIX);
        tokenDTO.setToken(stringToken);
        tokenDTO.setExpiresIn(expiresIn.atZone(ZoneId.systemDefault()).toEpochSecond());

        return tokenDTO;
    }

    public String getUsernameByToken(String token) {
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
}
