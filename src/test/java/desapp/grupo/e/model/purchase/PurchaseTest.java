package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.builder.product.OfferBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

public class PurchaseTest {

    private Purchase purchase;

    @BeforeEach
    public void setUp() {
        purchase = new Purchase();
    }

    @Test
    public void whenWeCreateANewPurchaseAndIfGetAllSubPurchasesThenShouldReturnAnEmptyList() {
        Assertions.assertTrue(purchase.getSubPurchases().isEmpty());
    }

    @Test
    public void whenAddAProductThenShouldCreateASubPurchaseWithThisProduct() {
        Product product = ProductBuilder.aProduct().anyProduct().build();

        purchase.addProduct(product);

        Assertions.assertEquals(1, purchase.getSubPurchases().size());
    }

    @Test
    public void whenAdd2ProductThatHasDifferentCommerceAsociatedThenShouldCreate2SubPurchase() {
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(1L).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).build();

        purchase.addProduct(productCommerce1);
        purchase.addProduct(productCommerce2);

        Assertions.assertEquals(2, purchase.getSubPurchases().size());
    }

    @Test
    public void IfAddAProductAndAfterRemoveItThenWhenGetAllSubPurchasesShouldReturnAnEmptyList() {
        Product product = ProductBuilder.aProduct().anyProduct().build();
        purchase.addProduct(product);

        purchase.removeProduct(product);

        Assertions.assertTrue(purchase.getSubPurchases().isEmpty());
    }

    @Test
    public void getTotalAmountOfProductsFromPurchaseShouldReturnTheSumOfAllSubPurchases() {
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(1L).withPrice(1000.0).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(2L).withPrice(500.0).build();

        purchase.addProduct(productCommerce1);
        purchase.addProduct(productCommerce2);

        Assertions.assertEquals(1500.0, purchase.getTotalAmount());
    }

    @Test
    public void addOfferToPurchaseShouldCreateASubpurchaseAboutCommerce() {
        Long idCommerce1 = 1L;
        Offer offer = OfferBuilder.aOffer().anyOffer().withIdCommerce(idCommerce1).build();

        purchase.addOffer(offer);

        Assertions.assertFalse(purchase.getSubPurchases().isEmpty());
        Assertions.assertNotNull(findOfferByIdCommerce(purchase.getSubPurchases(), idCommerce1));
    }

    @Test
    public void ifRemoveAOfferAndTheSubPurchaseHasNotAnotherOfferOrProductThenThisIsEliminatedToo() {
        Long idCommerce1 = 1L;
        Product product = ProductBuilder.aProduct().anyProduct().withIdCommerce(idCommerce1).build();
        purchase.addProduct(product);
        Offer offer = OfferBuilder.aOffer().anyOffer().withIdCommerce(idCommerce1).build();
        purchase.addOffer(offer);

        purchase.removeOffer(offer);

        Assertions.assertFalse(purchase.getSubPurchases().isEmpty());
        Assertions.assertTrue(purchase.getSubPurchases().get(0).getOffers().isEmpty());
        Assertions.assertFalse(purchase.getSubPurchases().get(0).getProducts().isEmpty());
    }

    @Test
    public void ifRemoveAOfferAndTheSubPurchaseHasProductsThenThisIsNotEliminatedToo() {
        Long idCommerce1 = 1L;
        Offer offer = OfferBuilder.aOffer().anyOffer().withIdCommerce(idCommerce1).build();
        purchase.addOffer(offer);

        purchase.removeOffer(offer);

        Assertions.assertTrue(purchase.getSubPurchases().isEmpty());
    }

    @Test
    public void getTotalAmountOfOffersFromPurchaseShouldReturnTheSumOfAllSubPurchases() {
        Long idCommerce1 = 1L;
        Long idCommerce2 = 2L;
        Product productCommerce1 = ProductBuilder.aProduct().withIdCommerce(idCommerce1).withPrice(1000.0).build();
        Product productCommerce2 = ProductBuilder.aProduct().withIdCommerce(idCommerce2).withPrice(500.0).build();
        Offer offer1 = OfferBuilder.aOffer().withIdCommerce(idCommerce1).withOff(20.0).withProduct(productCommerce1).build();
        Offer offer2 = OfferBuilder.aOffer().withIdCommerce(idCommerce2).withOff(10.0).withProduct(productCommerce2).build();

        purchase.addOffer(offer1);
        purchase.addOffer(offer2);

        Assertions.assertEquals(1250.0, purchase.getTotalAmount());
    }

    @Test
    public void getTotalAmountOfProductsAndOffersFromPurchaseShouldReturnTheSumOfAllSubPurchases() {
        Long idCommerce1 = 1L;
        Long idCommerce2 = 2L;
        Product product1Commerce1 = ProductBuilder.aProduct().withIdCommerce(idCommerce1).withPrice(300.0).build();
        Product product1Commerce2 = ProductBuilder.aProduct().withIdCommerce(idCommerce2).withPrice(200.0).build();
        purchase.addProduct(product1Commerce1);
        purchase.addProduct(product1Commerce2);
        Product product2Commerce1 = ProductBuilder.aProduct().withIdCommerce(idCommerce1).withPrice(1000.0).build();
        Product product2Commerce2 = ProductBuilder.aProduct().withIdCommerce(idCommerce2).withPrice(500.0).build();
        Offer offer1 = OfferBuilder.aOffer().withIdCommerce(idCommerce1).withOff(20.0).withProduct(product2Commerce1).build();
        Offer offer2 = OfferBuilder.aOffer().withIdCommerce(idCommerce2).withOff(10.0).withProduct(product2Commerce2).build();
        purchase.addOffer(offer1);
        purchase.addOffer(offer2);

        Assertions.assertEquals(1750.0, purchase.getTotalAmount());
    }

    private Offer findOfferByIdCommerce(List<SubPurchase> subPurchases, Long idCommerce) {
        Optional<SubPurchase> optSubpurchase = subPurchases.stream()
                .filter(subPurchase -> idCommerce.equals(subPurchase.getIdCommerce()))
                .findFirst();
        if(optSubpurchase.isPresent()) {
            SubPurchase subPurchase = optSubpurchase.get();
            return subPurchase.getOffers().stream()
                    .filter(offer -> idCommerce.equals(offer.getIdCommerce()))
                    .findFirst()
                    .get();
        }
        return null;
    }
}
