package desapp.grupo.e.webservice.controller.product;

import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import javax.validation.Valid;

@RestController
public class ProductController {

    private ProductService productService;
    private static final String PRODUCT_ID = "product_id";
    private static final String URL_BASE = "/product/";
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(URL_BASE + "/{" + PRODUCT_ID+ "}")        
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(new ProductDTO(product));
    }

    /*
    @PostMapping(URL_BASE)
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        Product product = convertDtoToModel(productDTO);
        Product newProduct = productService.save(product);
        productDTO.setId(newProduct.getId());
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    public Product converDtoModel(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName()); 
        product.setBrand(productDTO.getBrand());
        product.setPrice(productDTO.getPrice());
        product.setImg(productDTO.getImg());
        product.setStock(productDTO.getStock());
        return product;
    }
    */
}
