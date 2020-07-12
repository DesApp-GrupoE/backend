package desapp.grupo.e.service.exceptions;

import desapp.grupo.e.model.product.Product;

public class StockException extends RuntimeException {

    private Product product;

    public StockException(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
