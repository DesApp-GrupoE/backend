package desapp.grupo.e.persistence.product;

import desapp.grupo.e.ApplicationJpaRepositoryTest;
import desapp.grupo.e.model.builder.commerce.CommerceBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.dto.search.ProductSearchDTO;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.User;
import desapp.grupo.e.persistence.commerce.CommerceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = ApplicationJpaRepositoryTest.class)
public class ProductRepositoryJdbcTest {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    private CommerceRepository commerceRepository;
    private ProductRepositoryJdbcImpl productRepositoryJdbcImpl;

    @BeforeEach
    public void setUp() {
        User user = UserBuilder.aUser().anyUser().build();
        Commerce commerce = CommerceBuilder.aCommerce().anyCommerce().build();

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
        commerceRepository.save(commerce);

        productRepositoryJdbcImpl = new ProductRepositoryJdbcImpl(jdbcTemplate);
    }

    @AfterEach
    public void tearDown() {
        commerceRepository.deleteAll();
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
}
