package desapp.grupo.e.webservice.controller.product;

import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.mapper.ProductMapper;
import desapp.grupo.e.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    private static final String COMMERCE_ID = "commerce_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String URL_BASE = "/commerce/{" + COMMERCE_ID + "}/product";
    private static final String URL_BASE_DELETED = "/commerce/{" + COMMERCE_ID + "}/product/{" + PRODUCT_ID + "}";
    
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(URL_BASE)
    public ResponseEntity<List<ProductDTO>> getProductById(@PathVariable(COMMERCE_ID) Long commerceId) {
        List<Product> products = productService.findAllProductsByCommerce(commerceId);
        List<ProductDTO> productDTOS = products.stream().map(ProductDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(productDTOS);
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<ProductDTO> createProduct(@PathVariable(COMMERCE_ID) Long commerceId,
                                                    @Valid @RequestBody ProductDTO productDTO) {
        Product product = this.productMapper.mapDtoToModel(productDTO);
        Product newProduct = productService.save(commerceId, product);
        productDTO.setId(newProduct.getId());
        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(URL_BASE_DELETED)
    public ResponseEntity deleteCommerce(@PathVariable(COMMERCE_ID) Long commerceId, @PathVariable(PRODUCT_ID) Long productId) {
        productService.removeById(commerceId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(URL_BASE + "/csv")
    public ResponseEntity<List<ProductDTO>> createProductsByCsvFile(@PathVariable(COMMERCE_ID) Long commerceId, @RequestParam("file") MultipartFile file) {
        List<Product> productsCreated = this.productService.createProducts(commerceId, this.productMapper.mapCsvToModel(file));
        return new ResponseEntity<>(
                productsCreated.stream().map(ProductDTO::new).collect(Collectors.toList()),
                HttpStatus.CREATED);
    }
}
