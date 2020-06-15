package desapp.grupo.e.webservice.security;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.dto.auth.LoginRequestDTO;
import desapp.grupo.e.model.dto.auth.TokenDTO;
import desapp.grupo.e.service.auth.AuthService;
import desapp.grupo.e.service.jackson.JsonUtils;
import desapp.grupo.e.service.log.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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
import java.util.ArrayList;

/**
 *  Este filtro se encarga del login
 *  En el constructor definimos la ruta para realizar el login
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthService authService;
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, ApplicationContext ctx) {
        super();
        this.authenticationManager = authenticationManager;
        this.authService = ctx.getBean(AuthService.class);
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

    // Si el método anterior no tiro excepción y pudo devolver Authentication, entonces se crea el token
    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {
        TokenDTO tokenDTO = authService.createToken(((User) auth.getPrincipal()).getUsername());
        res.setContentType("application/json");
        PrintWriter writer = res.getWriter();
        writer.println(JsonUtils.toJson(tokenDTO));
        writer.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        Log.info("Authentication request failed: " + failed.toString());
        Log.exception(failed);
        ApiError apiError = new ApiError("Email or Password incorrect");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");

        PrintWriter writer = response.getWriter();
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        writer.println(objectWriter.writeValueAsString(apiError));
        writer.flush();
    }
}