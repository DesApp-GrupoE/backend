package desapp.grupo.e.webservice.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import desapp.grupo.e.model.dto.auth.LoginRequestDTO;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.service.log.Log;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

/**
 *  Este filtro se encarga del login
 *  En el constructor definimos la ruta para realizar el login
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/auth/login"); // Indico que se cree este endpoint para el login
    }

    // Chequea la autenticación del usuario. Este método utilizará
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            LoginRequestDTO loginRequest = new ObjectMapper().readValue(req.getInputStream(), LoginRequestDTO.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            Log.exception(e);
            throw new RuntimeException(e);
        }
    }

    // Si el método anterior no tiro excepción y puedo devolver Authentication, se crea el token
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {

        LocalDateTime expiresIn = LocalDateTime.now().plusDays(1);
        String stringToken = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(Date.from(expiresIn.atZone(ZoneId.systemDefault()).toInstant()))
                .sign(HMAC512(SecurityConstants.SECRET.getBytes()));

        res.setContentType("application/json");

        PrintWriter writer = res.getWriter();
        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setType(SecurityConstants.TOKEN_PREFIX);
        tokenDTO.setToken(stringToken);
        tokenDTO.setExpiresIn(expiresIn.atZone(ZoneId.systemDefault()).toEpochSecond());

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        writer.println(objectWriter.writeValueAsString(tokenDTO));
        writer.flush();
    }

//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
//        if (this.logger.isDebugEnabled()) {
//            this.logger.debug("Authentication request failed: " + failed.toString(), failed);
//            this.logger.debug("Updated SecurityContextHolder to contain null Authentication");
//            this.logger.debug("Delegating to authentication failure handler " + this.failureHandler);
//        }
//
//        this.rememberMeServices.loginFail(request, response);
//        this.failureHandler.onAuthenticationFailure(request, response, failed);
//    }
}