package desapp.grupo.e.service.search;

import desapp.grupo.e.model.dto.search.SearcherDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.ProductRepositoryJdbcImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductSearcherService {

    private ProductRepositoryJdbcImpl productRepositoryJdbcImpl;

    public ProductSearcherService(ProductRepositoryJdbcImpl productRepository) {
        this.productRepositoryJdbcImpl = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts(SearcherDTO searcherDTO) {
        if(searcherDTO.getAddressDTO().hasCoordenates()) {
            return this.productRepositoryJdbcImpl.findProductsInRadioKm(searcherDTO.getProductSearchDTO(),
                    searcherDTO.getAddressDTO().getLatitude(), searcherDTO.getAddressDTO().getLongitude(), searcherDTO.getAddressDTO().getKilometers());
        } else {
            return this.productRepositoryJdbcImpl.findProducts(searcherDTO.getProductSearchDTO());
        }
    }
}
