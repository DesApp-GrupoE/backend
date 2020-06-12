package desapp.grupo.e.service.search;

import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.dto.search.AddressDTO;
import desapp.grupo.e.model.dto.search.SearcherDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.persistence.product.ProductRepositoryJdbcImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductSearchServiceTest {

    private ProductRepositoryJdbcImpl productRepositoryJdbcImpl;
    private ProductSearcherService productSearcherService;

    @BeforeEach
    public void setUp() {
        productRepositoryJdbcImpl = mock(ProductRepositoryJdbcImpl.class);
        productSearcherService = new ProductSearcherService(productRepositoryJdbcImpl);
    }

    @Test
    public void getProductsWithPosition() {
        SearcherDTO searcherDTO = new SearcherDTO();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setLatitude(10.0);
        addressDTO.setLongitude(10.0);
        addressDTO.setKilometers(10);
        searcherDTO.setAddressDTO(addressDTO);
        Product product = ProductBuilder.aProduct().anyProduct().build();
        when(productRepositoryJdbcImpl.findProductsInRadioKm(any(), any(), any(), any())).thenReturn(Arrays.asList(product));

        List<Product> products = productSearcherService.findProducts(searcherDTO);
        Assertions.assertFalse(products.isEmpty());
    }

    @Test
    public void getProductsWithoutPosition() {
        SearcherDTO searcherDTO = new SearcherDTO();
        searcherDTO.setAddressDTO(new AddressDTO());
        Product product = ProductBuilder.aProduct().anyProduct().build();
        when(productRepositoryJdbcImpl.findProducts(any())).thenReturn(Arrays.asList(product));

        List<Product> products = productSearcherService.findProducts(searcherDTO);
        Assertions.assertFalse(products.isEmpty());
    }
}
