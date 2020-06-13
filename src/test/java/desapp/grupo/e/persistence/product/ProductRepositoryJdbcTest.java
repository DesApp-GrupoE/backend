package desapp.grupo.e.persistence.product;

import desapp.grupo.e.ApplicationJpaRepositoryTest;
import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.dto.search.ProductSearchDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationJpaRepositoryTest.class)
public class ProductRepositoryJdbcTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;
    private ProductRepositoryJdbcImpl productRepositoryJdbcImpl;

    @BeforeEach
    public void setUp() {
        Double baseLatitud = -34.725805;
        Double baseLongitude = -58.252009;

        User user = UserBuilder.aUser().anyUser().build();
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce()
                .withLatitude(baseLatitud).withLongitude(baseLongitude)
                .build();

        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("url")
                .build();
        Product product2 = ProductBuilder.aProduct()
                .withName("Coca Cola 2.25L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .withImg("url")
                .build();
        Product product3 = ProductBuilder.aProduct()
                .withName("Coca Cola Light 1.5L").withBrand("Coca Cola")
                .withStock(100).withPrice(120.0)
                .withImg("url")
                .build();

        commerce.getProducts().add(product1);
        commerce.getProducts().add(product2);
        commerce.getProducts().add(product3);

        user.setCommerce(commerce);
        userRepository.save(user);

        productRepositoryJdbcImpl = new ProductRepositoryJdbcImpl(jdbcTemplate);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void getAllProductsIfProductSearchDtoIsNull() {
        List<Product> products = productRepositoryJdbcImpl.findProducts(null);

        Assertions.assertEquals(3, products.size());
    }

    @Test
    public void getAllProductsIfProductSearchDtoIsEmpty() {
        List<Product> products = productRepositoryJdbcImpl.findProducts(new ProductSearchDTO());

        Assertions.assertEquals(3, products.size());
    }

    @Test
    public void getProductsLikeName() {
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setName("Fideos");
        List<Product> products = productRepositoryJdbcImpl.findProducts(productSearchDTO);

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void getProductsLikeNameEmptyShouldReturnAllProducts() {
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setName("");
        List<Product> products = productRepositoryJdbcImpl.findProducts(productSearchDTO);

        Assertions.assertEquals(3, products.size());
    }

    @Test
    public void getProductsByBrand() {
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setBrand("Coca Cola");
        List<Product> products = productRepositoryJdbcImpl.findProducts(productSearchDTO);

        Assertions.assertEquals(2, products.size());
    }

    @Test
    public void getProductsByNameAndBrand() {
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setName("Light");
        productSearchDTO.setBrand("Coca Cola");
        List<Product> products = productRepositoryJdbcImpl.findProducts(productSearchDTO);

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void getProductsByLatitudeLongitudeAndProductSearch() {
        final Double LAT_LESS_THAN_1KM = -34.725269;
        final Double LNG_LESS_THAN_1KM = -58.250948;
        final Integer KMs = 1;
        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("url")
                .build();
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setName("Light");
        productSearchDTO.setBrand("Coca Cola");

        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        productRepositoryJdbcImpl = new ProductRepositoryJdbcImpl(jdbcTemplate);
        when(jdbcTemplate.query(anyString(), anyMap(), any(ProductMapper.class)))
                .thenReturn(Arrays.asList(product1));

        List<Product> products = productRepositoryJdbcImpl.findProductsInRadioKm(
                productSearchDTO, LAT_LESS_THAN_1KM, LNG_LESS_THAN_1KM, KMs);

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void getProductsByLatitudeLongitudeWithoutProductSearch() {
        final Double LAT_LESS_THAN_1KM = -34.725269;
        final Double LNG_LESS_THAN_1KM = -58.250948;
        final Integer KMs = 1;
        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("url")
                .build();

        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        productRepositoryJdbcImpl = new ProductRepositoryJdbcImpl(jdbcTemplate);
        when(jdbcTemplate.query(anyString(), anyMap(), any(ProductMapper.class)))
                .thenReturn(Arrays.asList(product1));

        List<Product> products = productRepositoryJdbcImpl.findProductsInRadioKm(
                null, LAT_LESS_THAN_1KM, LNG_LESS_THAN_1KM, KMs);

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void getProductsByLatitudeLongitudeWithProductSearchWithNameEmptyShouldReturnAllProducts() {
        final Double LAT_LESS_THAN_1KM = -34.725269;
        final Double LNG_LESS_THAN_1KM = -58.250948;
        final Integer KMs = 1;
        Product product1 = ProductBuilder.aProduct()
                .withName("Fideos 500gr").withBrand("Luchetti")
                .withStock(100).withPrice(50.0)
                .withImg("url")
                .build();
        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
        productSearchDTO.setName("");

        NamedParameterJdbcTemplate jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
        productRepositoryJdbcImpl = new ProductRepositoryJdbcImpl(jdbcTemplate);
        when(jdbcTemplate.query(anyString(), anyMap(), any(ProductMapper.class)))
                .thenReturn(Arrays.asList(product1));

        List<Product> products = productRepositoryJdbcImpl.findProductsInRadioKm(
                productSearchDTO, LAT_LESS_THAN_1KM, LNG_LESS_THAN_1KM, KMs);

        Assertions.assertEquals(1, products.size());
    }


//  Estos tests solo se pueden probar con una base local de postgresql
//  ya que estoy usando funciones especificas de postgresql que H2 no se banca
//  Estos testean el uso de Point
//
//    @Test
//    public void getProductsByLatitudeLongitudeInRadioLessThan1Km() {
//        final Double LAT_LESS_THAN_1KM = -34.725269;
//        final Double LNG_LESS_THAN_1KM = -58.250948;
//        final Integer KMs = 1;
//        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
//        productSearchDTO.setName("Light");
//        productSearchDTO.setBrand("Coca Cola");
//        List<Product> products = productRepositoryJdbcImpl.findProductsInRadioKm(
//                productSearchDTO, LAT_LESS_THAN_1KM, LNG_LESS_THAN_1KM, KMs);
//
//        Assertions.assertEquals(1, products.size());
//    }
//
//    @Test
//    public void getProductsByLatitudeLongitudeInRadioGreaterThan1Km() {
//        final Double LAT_GREATER_THAN_1KM = -34.7270397;
//        final Double LNG_GREATER_THAN_1KM = -58.2679150;
//        final Integer KMs = 1;
//        ProductSearchDTO productSearchDTO = new ProductSearchDTO();
//        productSearchDTO.setName("Light");
//        productSearchDTO.setBrand("Coca Cola");
//        List<Product> products = productRepositoryJdbcImpl.findProductsInRadioKm(
//                productSearchDTO, LAT_GREATER_THAN_1KM, LNG_GREATER_THAN_1KM, KMs);
//
//        Assertions.assertEquals(0, products.size());
//    }
}
