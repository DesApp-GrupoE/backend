package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.builder.ProductBuilder;
import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SubPurchaseTest {

    @Test
    public void whenCreateANewSubPurchaseAndGetAllProductsShouldReturnAEmptyList() {
        SubPurchase subPurchase = new SubPurchase();

        Assertions.assertTrue(subPurchase.getProducts().isEmpty());
    }

    @Test
    public void whenAddAProductToSubPurchaseAndGetAllProductsShouldReturnAListWithTheSameProductAdded() throws BusinessException {
        SubPurchase subPurchase = new SubPurchase();
        Product product = ProductBuilder.aProduct().anyProduct().build();

        subPurchase.addProduct(product);
        List<Product> products = subPurchase.getProducts();

        Assertions.assertEquals(1, products.size());
    }

    @Test
    public void whenAddAProductToSubPurchaseWithSpecificCommerceThenTheSubPurchaseShouldBeLinkedToThisCustomer() throws BusinessException {
        SubPurchase subPurchase = new SubPurchase();
        Product product = ProductBuilder.aProduct().withIdCommerce(1L).build();

        subPurchase.addProduct(product);

        Assertions.assertEquals(1L, subPurchase.getIdCommerce());
    }

    @Test
    public void whenAddAProductWithSpecificCommerceAndAfterAddAnotherProductWithDifferentCommerceShouldThrowAnBusinessException() throws BusinessException {
        SubPurchase subPurchase = new SubPurchase();
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(1L).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).build();

        subPurchase.addProduct(productCommerce1);
        Assertions.assertThrows(BusinessException.class, () ->  subPurchase.addProduct(productCommerce2), "Can't add a product of a different commerce in a subpurchase with a commerce already asociated");
    }

    @Test
    public void whenAddAProductInANewSubPurchaseAndAfterRemoveThisFromSubPurchaseThenIfGetAllProductsShouldReturnAEmptyList() throws BusinessException {
        SubPurchase subPurchase = new SubPurchase();
        Product product = ProductBuilder.aProduct().anyProduct().build();

        subPurchase.addProduct(product);
        subPurchase.removeProduct(product);

        Assertions.assertTrue(subPurchase.getProducts().isEmpty());
    }

    @Test
    public void WhenGetTotalAmountThenShouldReturnTheSumOfAllProducts() throws BusinessException {
        SubPurchase subPurchase = new SubPurchase();
        Product product1 = ProductBuilder.aProduct().withIdCommerce(1L).withPrice(150.0).build();
        Product product2 = ProductBuilder.aProduct().withIdCommerce(1L).withPrice(200.0).build();

        subPurchase.addProduct(product1);
        subPurchase.addProduct(product2);

        Assertions.assertEquals(350.0, subPurchase.getTotalAmount());
    }
}
