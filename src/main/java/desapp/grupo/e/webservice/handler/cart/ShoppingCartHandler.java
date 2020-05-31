package desapp.grupo.e.webservice.handler.cart;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.cart.ShoppingCartController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ShoppingCartController.class)
public class ShoppingCartHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        Log.info(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
