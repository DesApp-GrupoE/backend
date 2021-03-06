package desapp.grupo.e.webservice.handler;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.persistence.exception.EmailRegisteredException;
import desapp.grupo.e.service.exceptions.AuthenticationCode2FAException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.AuthController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = AuthController.class)
public class AuthControllerHandler {

    @ExceptionHandler({EmailRegisteredException.class})
    public ResponseEntity<ApiError> handleEmailRegisteredException(EmailRegisteredException e) {
        Log.debug(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e) {
        Log.debug(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AuthenticationCode2FAException.class})
    public ResponseEntity<ApiError> handleAuthenticationCode2FAException(AuthenticationCode2FAException e) {
        Log.debug(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
