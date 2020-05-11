package desapp.grupo.e.model.product;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ProductTest {

    @Test
    public void createProductShouldHasNameChocolate() {
        String productNameExpected = "chocolate";
        Product product = ProductBuilder.aProduct().withName(productNameExpected).build();

        Assertions.assertEquals(product.getName(), productNameExpected);
    }

    @Test
    public void createProductShouldHasBrandMilka() {
        String productBrandExpected = "milka";
        Product product = ProductBuilder.aProduct().withBrand(productBrandExpected).build();

        Assertions.assertEquals(product.getBrand(), productBrandExpected);
    }

    @Test
    public void createProductShouldHasPrice10() {
        Double productPriceExpected = 10.0;
        Product product = ProductBuilder.aProduct().withPrice(productPriceExpected).build();

        Assertions.assertEquals(product.getPrice(), productPriceExpected);
    }

    @Test
    public void createProductShouldHasStock50() {
        Integer productStockExpected = 50;
        Product product = ProductBuilder.aProduct().withStock(productStockExpected).build();

        Assertions.assertEquals(product.getStock(), productStockExpected);
    }

    @Test
    public void createProductShouldHasIDCustomer() {
        Long productIDCommerceExpected = 1L;
        Product product = ProductBuilder.aProduct().withIdCommerce(productIDCommerceExpected).build();

        Assertions.assertEquals(product.getIdCommerce(), productIDCommerceExpected);
    }
}