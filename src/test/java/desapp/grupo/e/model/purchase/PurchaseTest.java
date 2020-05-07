package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.builder.ProductBuilder;
import desapp.grupo.e.model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PurchaseTest {

    @Test
    public void whenWeCreateANewPurchaseAndIfGetAllSubPurchasesThenShouldReturnAnEmptyList() {
        Purchase purchase = new Purchase();

        Assertions.assertTrue(purchase.getSubPurchases().isEmpty());
    }

    @Test
    public void whenAddAProductThenShouldCreateASubPurchaseWithThisProduct() {
        Product product = ProductBuilder.aProduct().anyProduct().build();
        Purchase purchase = new Purchase();

        purchase.addProduct(product);

        Assertions.assertEquals(1, purchase.getSubPurchases().size());
    }

    @Test
    public void whenAdd2ProductThatHasDifferentCommerceAsociatedThenShouldCreate2SubPurchase() {
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(1L).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).build();
        Purchase purchase = new Purchase();

        purchase.addProduct(productCommerce1);
        purchase.addProduct(productCommerce2);

        Assertions.assertEquals(2, purchase.getSubPurchases().size());
    }

    @Test
    public void IfAddAProductAndAfterRemoveItThenWhenGetAllSubPurchasesShouldReturnAnEmptyList() {
        Product product = ProductBuilder.aProduct().anyProduct().build();
        Purchase purchase = new Purchase();
        purchase.addProduct(product);

        purchase.removeProduct(product);

        Assertions.assertTrue(purchase.getSubPurchases().isEmpty());
    }

    @Test
    public void getTotalAmountFromPurchaseShouldReturnTheSumOfAllSubPurchases() {
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(1L).withPrice(1000.0).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).withPrice(500.0).build();
        Purchase purchase = new Purchase();

        purchase.addProduct(productCommerce1);
        purchase.addProduct(productCommerce2);

        Assertions.assertEquals(1500.0, purchase.getTotalAmount());
    }
}
