package desapp.grupo.e.webservice.handler;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.persistence.exception.EmailRegisteredException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.LoginController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = LoginController.class)
public class LoginControllerHandler {

    @ExceptionHandler({EmailRegisteredException.class})
    public ResponseEntity<ApiError> handleEmailRegisteredException(EmailRegisteredException e) {
        Log.debug(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }


}
