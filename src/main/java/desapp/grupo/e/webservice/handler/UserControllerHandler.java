package desapp.grupo.e.webservice.handler;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.webservice.controller.UserController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = UserController.class)
public class UserControllerHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleEntityNotFound(ResourceNotFoundException e) {
        return new ResponseEntity<>(new ApiError("User not found"), HttpStatus.NOT_FOUND);
    }
}
