package desapp.grupo.e.model.user;

import desapp.grupo.e.model.builder.product.OfferBuilder;
import desapp.grupo.e.model.builder.product.ProductBuilder;
import desapp.grupo.e.model.builder.user.UserBuilder;
import desapp.grupo.e.model.builder.product.CategoryAlertBuilder;
import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.purchase.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

public class UserTest {

    @Test
    public void whenANewUserGetCategoryAlertsThenShouldGetAnEmptyList() {
        User user = UserBuilder.aUser().anyUser().build();

        Assertions.assertTrue(user.getCategoryAlerts().isEmpty());
    }

    @Test
    public void whenAUserAddACategoryAlertThenCategoryAlertsIsNotEmpty() {
        User user = UserBuilder.aUser().anyUser().build();
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().anyCategoryAlert().build();

        user.addCategoryAlert(categoryAlert);

        Assertions.assertFalse(user.getCategoryAlerts().isEmpty());
    }

    @Test
    public void whenAUserHasA1CategoryAlertAndRemoveItThenWhenGetCategoryAlertsThisListsIsEmpty() {
        User user = UserBuilder.aUser().anyUser().build();
        CategoryAlert categoryAlert = CategoryAlertBuilder.aCategoryAlert().anyCategoryAlert().build();
        user.addCategoryAlert(categoryAlert);

        user.removeCategoryAlert(categoryAlert);

        Assertions.assertTrue(user.getCategoryAlerts().isEmpty());
    }

    @Test
    public void aNewUserHasNotAPurchases() {
        User user = UserBuilder.aUser().anyUser().build();

        Assertions.assertTrue(user.getPurchases().isEmpty());
    }

    @Test
    public void aNewUserHasNotACurrentPurchase() {
        User user = UserBuilder.aUser().anyUser().build();

        Assertions.assertNull(user.getCurrentPurchase());
    }

    @Test
    public void whenANewUserAddAProductThenHasACurrentPurchaseThatShouldHasASubPurchaseWithThisProduct() {
        Long idCommerce = 1L;
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();

        user.addProduct(product);

        Purchase currentPurchase = user.getCurrentPurchase();
        Assertions.assertNotNull(currentPurchase);
        Assertions.assertFalse(currentPurchase.getSubPurchases().isEmpty());
    }

    @Test
    public void whenAUserWith1ProductAndRemoveItThenTheCurrentPurchaseShouldHasASubPurchaseEmpty() {
        Long idCommerce = 1L;
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        user.addProduct(product);

        user.removeProduct(product);

        Purchase currentPurchase = user.getCurrentPurchase();
        Assertions.assertNotNull(currentPurchase);
        Assertions.assertTrue(currentPurchase.getSubPurchases().isEmpty());
    }

    @Test
    public void whenAUserFinalizeAPurchaseThenTheCurrentPurchaseIsSavedInTheListOfPurchasesWithCurrentLocalDateTimeAndShouldNotHasdAlreadyCurrentPurchase() throws BusinessException {
        Long idCommerce = 1L;
        LocalDateTime purchaseDate = LocalDateTime.now();
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        user.addProduct(product);

        user.finalizePurchase(purchaseDate);

        List<Purchase> purchases = user.getPurchases();
        Assertions.assertEquals(1, purchases.size());
        Assertions.assertEquals(purchaseDate, purchases.get(0).getDatePurchase());
        Assertions.assertNull(user.getCurrentPurchase());
    }

    @Test
    public void whenANewUserAddAOfferThenHasACurrentPurchaseThatShouldHasASubPurchaseWithThisOffer() {
        Long idCommerce = 1L;
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        Offer offer = OfferBuilder.aOffer().withIdCommerce(idCommerce).withOff(10.0).withProduct(product).build();

        user.addOffer(offer);

        Purchase currentPurchase = user.getCurrentPurchase();
        Assertions.assertNotNull(currentPurchase);
        Assertions.assertFalse(currentPurchase.getSubPurchases().isEmpty());
        Assertions.assertEquals(idCommerce, currentPurchase.getSubPurchases().get(0).getIdCommerce());
    }

    @Test
    public void ifRemoveTheOnlyOfferInPurchaseThenPurchaseHasNotSubPurchases() {
        Long idCommerce = 1L;
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        Offer offer = OfferBuilder.aOffer().withIdCommerce(idCommerce).withOff(10.0).withProduct(product).build();
        user.addOffer(offer);

        user.removeOffer(offer);

        Purchase currentPurchase = user.getCurrentPurchase();
        Assertions.assertNotNull(currentPurchase);
        Assertions.assertTrue(currentPurchase.getSubPurchases().isEmpty());
    }

    @Test
    public void finalizeAPurchaseWithOffersThenShouldAddThisToPurchases() throws BusinessException {
        Long idCommerce = 1L;
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        Offer offer = OfferBuilder.aOffer().withIdCommerce(idCommerce).withOff(10.0).withProduct(product).build();
        user.addOffer(offer);

        user.finalizePurchase(LocalDateTime.now());

        Assertions.assertNull(user.getCurrentPurchase());
        Assertions.assertFalse(user.getPurchases().isEmpty());
    }

    @Test
    public void aUserCanNotFinalizeACurrentPurchaseIfTheSameHasNotProductsNeitherOffers() {
        Long idCommerce = 1L;
        LocalDateTime purchaseDate = LocalDateTime.now();
        User user = UserBuilder.aUser().anyUser().build();
        Product product = ProductBuilder.aProduct().withIdCommerce(idCommerce).build();
        user.addProduct(product);
        user.removeProduct(product);

        Assertions.assertThrows(BusinessException.class, () -> user.finalizePurchase(purchaseDate));
    }

}
