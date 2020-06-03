package desapp.grupo.e.service.product;

import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;

public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long idProduct) {
        return productRepository.findById(idProduct)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found", idProduct)));
    }

    /*
    public Product postProduct() {
        
    }
    */
}
