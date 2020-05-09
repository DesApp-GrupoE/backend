package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.builder.ProductBuilder;
import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SubPurchaseTest {

    private SubPurchase subPurchase;
    private Long idCommerce1;

    @BeforeEach
    public void setUp() {
        this.idCommerce1 = 1L;
        this.subPurchase = new SubPurchase(this.idCommerce1);
    }

    @Test
    public void whenCreateANewSubPurchaseAndGetItsIdCommerceThenReturnTheSameIdCommerceThatWasUsedInCreation() {
        Assertions.assertEquals(idCommerce1, subPurchase.getIdCommerce());
    }

    @Test
    public void whenCreateANewSubPurchaseAndGetAllProductsShouldReturnAEmptyList() {
        Assertions.assertTrue(subPurchase.getProducts().isEmpty());
    }

    @Test
    public void whenAddAProductToSubPurchaseAndGetAllProductsShouldReturnAListWithTheSameProductAdded() throws BusinessException {
        Product product = ProductBuilder.aProduct().anyProduct().build();

        subPurchase.addProduct(product);
        List<Product> products = subPurchase.getProducts();

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void whenAddAProductWithDifferentCommerceShouldThrowAnBusinessException() {
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).build();

        Assertions.assertThrows(BusinessException.class, () ->  subPurchase.addProduct(productCommerce2), "Can't add a product of a different commerce in a subpurchase with a commerce already asociated");
    }

    @Test
    public void whenAddAProductInANewSubPurchaseAndAfterRemoveThisFromSubPurchaseThenIfGetAllProductsShouldReturnAEmptyList() throws BusinessException {
        Product product = ProductBuilder.aProduct().anyProduct().build();

        subPurchase.addProduct(product);
        subPurchase.removeProduct(product);

        Assertions.assertTrue(subPurchase.getProducts().isEmpty());
    }

    @Test
    public void WhenGetTotalAmountThenShouldReturnTheSumOfAllProducts() throws BusinessException {
        Product product1 = ProductBuilder.aProduct().withIdCommerce(idCommerce1).withPrice(150.0).build();
        Product product2 = ProductBuilder.aProduct().withIdCommerce(idCommerce1).withPrice(200.0).build();

        subPurchase.addProduct(product1);
        subPurchase.addProduct(product2);

        Assertions.assertEquals(350.0, subPurchase.getTotalAmount());
    }
}
