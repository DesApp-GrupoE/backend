package desapp.grupo.e.webservice.controller.search;

import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.dto.search.SearcherDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.service.commerce.CommerceService;
import desapp.grupo.e.service.search.ProductSearcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class ProductSearcherController {

    private static final String URL_BASE = "/products";

    private ProductSearcherService productSearcherService;
    private CommerceService commerceService;

    public ProductSearcherController(ProductSearcherService productSearcherService, CommerceService commerceService) {
        this.productSearcherService = productSearcherService;
        this.commerceService = commerceService;
    }

    @PostMapping(URL_BASE)
    public ResponseEntity<List<ProductDTO>> searchProducts(@Valid @RequestBody SearcherDTO searcherDTO) {
        List<Product> products = this.productSearcherService.findProducts(searcherDTO);
        List<Long> idsCommerce = products.stream().map(Product::getIdCommerce).distinct().collect(Collectors.toList());
        List<Commerce> commerceList = this.commerceService.getAllCommerceById(idsCommerce);
        List<ProductDTO> productDTOS = products.stream()
                .map(product -> this.mapToDto(product, commerceList))
                .collect(Collectors.toList());

        return ResponseEntity.ok(productDTOS);
    }

    private ProductDTO mapToDto(Product product, List<Commerce> commerceList) {
        ProductDTO productDTO = new ProductDTO(product);
        Optional<Commerce> optCommerce = commerceList.stream()
                .filter(c -> product.getIdCommerce().equals(c.getId()))
                .findFirst();
        if(optCommerce.isPresent()) {
            Commerce commerce = optCommerce.get();
            productDTO.setNameCommerce(commerce.getName());
        }
        return productDTO;
    }

}
