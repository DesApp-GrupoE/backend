package desapp.grupo.e.webservice.controller.search;

import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.dto.search.SearcherDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.service.search.ProductSearcherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductSearcherController {

    private static final String URL_BASE = "/products";

    private ProductSearcherService productSearcherService;

    public ProductSearcherController(ProductSearcherService productSearcherService) {
        this.productSearcherService = productSearcherService;
    }

    @PostMapping(URL_BASE)
    public ResponseEntity searchProducts(@Valid @RequestBody SearcherDTO searcherDTO) {
        List<Product> products = this.productSearcherService.findProducts(searcherDTO);
        List<ProductDTO> productDTOS = products.stream()
                .map(ProductDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOS);
    }

}
