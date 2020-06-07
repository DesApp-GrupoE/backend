package desapp.grupo.e.service.product;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import desapp.grupo.e.persistence.product.ProductRepository;
import desapp.grupo.e.persistence.exception.ProductDuplicatedException;
import desapp.grupo.e.service.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


public class ProductService {

    private CommerceRepository commerceRepository;
    private ProductRepository productRepository;
    
    public ProductService(CommerceRepository commerceRepository, ProductRepository productRepository) {
        this.commerceRepository = commerceRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product save(long commerceId, Product product) {
        if(productRepository.existProductInDatabase(product.getName())) {
            throw new ProductDuplicatedException(String.format("Product already has the commerce '%s'", product.getName()));
        }
        Commerce commerce = findProductById(commerceId);
        List<Product> productsOfCommerce = commerce.getProducts();
        productsOfCommerce.add(product);
        commerce.setProducts(productsOfCommerce);
        this.commerceRepository.save(commerce);
        return commerce.getProducts().get(commerce.getProducts().size() - 1);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product %s not found", productId)));
    }

    private Commerce findProductById(Long commerceId) {
        return commerceRepository.findById(commerceId)
                    .orElseThrow(() -> new ResourceNotFoundException(String.format("Commerce %s not found", commerceId)));
    }
}
