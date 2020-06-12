package desapp.grupo.e.service.search;

import desapp.grupo.e.model.dto.search.PositionStack;
import desapp.grupo.e.model.dto.search.SearcherDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.ProductRepositoryJdbcImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductSearcherService {

    private ProductRepositoryJdbcImpl productRepositoryJdbcImpl;
    private PositionStackService positionStackService;

    public ProductSearcherService(ProductRepositoryJdbcImpl productRepository, PositionStackService positionStackService) {
        this.productRepositoryJdbcImpl = productRepository;
        this.positionStackService = positionStackService;
    }

    public List<Product> findProducts(SearcherDTO searcherDTO) {
        Optional<PositionStack> opsPosition = this.positionStackService.findPositionByAddress(searcherDTO.getAddressDTO());
        if(!opsPosition.isPresent()) {
            return this.productRepositoryJdbcImpl.findProducts(searcherDTO.getProductSearchDTO());
        } else {
            return this.productRepositoryJdbcImpl.findProductsInRadioKm(searcherDTO.getProductSearchDTO(),
                    opsPosition.get().getLatitude(), opsPosition.get().getLongitude(), searcherDTO.getAddressDTO().getKilometers());
        }
    }
}
