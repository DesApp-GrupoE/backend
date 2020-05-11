package desapp.grupo.e.model.product;
import desapp.grupo.e.model.builder.ProductBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ProductTest {
    @Test
    public void test() {
        Assertions.assertEquals(1, 1);
    }

    @Test
    public void createMerchantShouldHasRoleMERCHANT_ByDefult() {
        Product product = ProductBuilder.aProduct().anyProduct().build();
        String productNameExpected = "test";

     Assertions.assertEquals(product.getName(), productNameExpected);
    }
}
