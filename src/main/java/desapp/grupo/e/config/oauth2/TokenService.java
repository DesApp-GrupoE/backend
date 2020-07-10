package desapp.grupo.e.config.oauth2;

import com.auth0.jwt.exceptions.TokenExpiredException;
import desapp.grupo.e.config.oauth2.model.UserPrincipal;
import desapp.grupo.e.config.oauth2.properties.ApplicationProperties;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.security.SecurityConstants;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class TokenService {

    private ApplicationProperties appProperties;
    private List<String> blackListToken;

    public TokenService(ApplicationProperties appProperties) {
        this.appProperties = appProperties;
        this.blackListToken = new ArrayList<>();
    }

    public TokenDTO createToken(Authentication authentication) {
        LocalDateTime expiresIn = LocalDateTime.now().plusHours(1);
        String tokenValue = this.createTokenWithExpiration(authentication, expiresIn);
        return new TokenDTO(SecurityConstants.TOKEN_PREFIX, tokenValue, expiresIn);
    }

    public String createTokenValue(Authentication authentication) {
        LocalDateTime expiresIn = LocalDateTime.now().plusHours(1);
        return this.createTokenWithExpiration(authentication, expiresIn);
    }

    private String createTokenWithExpiration(Authentication authentication, LocalDateTime expiredTime) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(expiredTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            if(this.isInBlackList(authToken)) {
                throw new TokenExpiredException("Token is in black List");
            }
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            Log.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            Log.error("Invalid JWT token");
        } catch (ExpiredJwtException | TokenExpiredException ex) {
            Log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            Log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            Log.error("JWT claims string is empty.");
        }
        return false;
    }

    public void addTokenToBlackList(String bearerToken) {
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            this.blackListToken.add(bearerToken);
        }
    }

    private Boolean isInBlackList(String token) {
        return this.blackListToken.contains(token);
    }
}