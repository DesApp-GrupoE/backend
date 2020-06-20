package desapp.grupo.e.webservice.handler.commerce;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.persistence.exception.CommerceDuplicatedException;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.commerce.CommerceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CommerceController.class)
public class CommerceControllerHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        Log.info(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CommerceDuplicatedException.class})
    public ResponseEntity<ApiError> handleCommerceDuplicatedException(CommerceDuplicatedException e) {
        Log.info(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
