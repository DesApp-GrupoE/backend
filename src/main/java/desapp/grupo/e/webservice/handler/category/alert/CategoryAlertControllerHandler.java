package desapp.grupo.e.webservice.handler.category.alert;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.persistence.exception.CategoryDuplicatedException;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.log.Log;
import desapp.grupo.e.webservice.controller.category.alert.CategoryAlertController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = CategoryAlertController.class)
public class CategoryAlertControllerHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFoundException e) {
        Log.info(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CategoryDuplicatedException.class})
    public ResponseEntity<ApiError> handleCategoryDuplicatedException(CategoryDuplicatedException e) {
        Log.info(e.getMessage());
        ApiError apiError = new ApiError(e.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }
}
