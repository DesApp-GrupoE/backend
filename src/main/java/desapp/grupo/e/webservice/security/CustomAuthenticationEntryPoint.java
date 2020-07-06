package desapp.grupo.e.webservice.security;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.service.jackson.JsonUtils;
import desapp.grupo.e.service.log.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException, ServletException {
        String endpoint = req.getContextPath() + req.getServletPath();
        Log.info(String.format("Try to call '%s' with invalid token", endpoint));
        flushApiError(res, new ApiError("Access Denied: Your token is invalid"));
    }

    private void flushApiError(HttpServletResponse res, ApiError apiError) throws IOException {
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setStatus(HttpStatus.FORBIDDEN.value());
        PrintWriter writer = res.getWriter();
        writer.println(JsonUtils.toJson(apiError));
        writer.flush();
    }
}
