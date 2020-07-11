package desapp.grupo.e.webservice.handler.product;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.service.exceptions.BadRequestException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.product.ProductController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = ProductController.class)
public class ProductControllerHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(BadRequestException e) {
        Log.debug(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
