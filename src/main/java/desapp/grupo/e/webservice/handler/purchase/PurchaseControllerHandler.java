package desapp.grupo.e.webservice.handler.purchase;

import desapp.grupo.e.model.dto.ApiError;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import desapp.grupo.e.service.exceptions.StockException;
import desapp.grupo.e.webservice.controller.purchase.PurchaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(assignableTypes = PurchaseController.class)
public class PurchaseControllerHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> createApiResponse(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockException.class)
    public ResponseEntity<ApiError> handleStockException(StockException e) {
        Product product = e.getProduct();
        ApiError apiError = new ApiError(String.format("Producto '%s' sin stock suficiente", product.getName()));
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }
}
