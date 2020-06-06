package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.builder.cart.CartProductBuilder;
import desapp.grupo.e.model.purchase.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        this.shoppingCart = new ShoppingCart();
    }

    @Test
    public void aNewShoppingCartShouldHasEmptyListOfCartProducts() {
        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void addCartProductToShoppingCart() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct().build();

        shoppingCart.addProduct(cartProduct);

        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
    }

    @Test
    public void removeProductFromShoppingCart() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct().build();
        shoppingCart.addProduct(cartProduct);

        shoppingCart.removeProduct(cartProduct);

        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void removeProductByIdFromShoppingCart() {
        Long productId = 1L;
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct().withProductId(productId).build();
        shoppingCart.addProduct(cartProduct);

        shoppingCart.removeProductById(productId);

        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void getTotalAmount() {
        CartProduct cartProduct1 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withProductId(1L).withPrice(100.0).withQuantity(1).build();
        CartProduct cartProduct2 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withProductId(2L).withPrice(200.0).withQuantity(1).build();

        shoppingCart.addProduct(cartProduct1);
        shoppingCart.addProduct(cartProduct2);

        Assertions.assertEquals(300.0, shoppingCart.getTotalAmount());
    }

    @Test
    public void generatePurchaseShouldReturnAListOfPurchaseWithOneElement() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct().withCommerceId(1L).build();
        shoppingCart.addProduct(cartProduct);

        List<Purchase> purchases = shoppingCart.generatePurchase();

        Assertions.assertEquals(1, purchases.size());
        Assertions.assertEquals(1L, purchases.get(0).getCommerceId());
        Assertions.assertEquals(1, purchases.get(0).getCartProducts().size());
    }

    @Test
    public void addCartProductToShoppingCartThatHasTheSameProductThenShouldSumQuantities() {
        Long commerceId1 = 1L;
        Long productId1= 1L;
        CartProduct cartProduct1 = CartProductBuilder.aProductCartBuilder()
                .anyProduct().withCommerceId(commerceId1).withProductId(productId1).withQuantity(1).build();
        CartProduct cartProduct2 = CartProductBuilder.aProductCartBuilder()
                .anyProduct().withCommerceId(commerceId1).withProductId(productId1).withQuantity(2).build();

        shoppingCart.addProduct(cartProduct1);
        shoppingCart.addProduct(cartProduct2);

        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
        Assertions.assertEquals(3, shoppingCart.getCartProducts().get(0).getQuantity());
    }

    @Test
    public void addACartProductFromOfferAndCartHasAProductWithSameProductIdThenShouldHasTwoProducts() {
        Long commerceId1 = 1L;
        Long productId1= 1L;
        Long offerId1 = 1L;
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1)
                .withQuantity(1).build();
        CartProduct cartProductWithOffer = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartProduct);

        shoppingCart.addProduct(cartProductWithOffer);

        Assertions.assertEquals(2, shoppingCart.getCartProducts().size());
    }

    @Test
    public void removeOfferAndIfExistAProductWithSameProductIdButIsNotAOfferShouldDeleteOnlyTheOffer() {
        Long commerceId1 = 1L;
        Long productId1= 1L;
        Long offerId1 = 1L;
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1)
                .withQuantity(1).build();
        CartProduct cartProductWithOffer = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartProduct);
        shoppingCart.addProduct(cartProductWithOffer);

        shoppingCart.removeOfferById(offerId1);

        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
        Assertions.assertNull(shoppingCart.getCartProducts().get(0).getOfferId());
    }

    @Test
    public void removeProductAndIfExistsAnOfferWithSameProductIdShouldDeleteOnlyTheProduct() {
        Long commerceId1 = 1L;
        Long productId1= 1L;
        Long offerId1 = 1L;
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1)
                .withQuantity(1).build();
        CartProduct cartProductWithOffer = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartProduct);
        shoppingCart.addProduct(cartProductWithOffer);

        shoppingCart.removeProductById(productId1);

        Assertions.assertEquals(1, shoppingCart.getCartProducts().size());
        Assertions.assertEquals(offerId1, shoppingCart.getCartProducts().get(0).getOfferId());
    }


    @Test
    public void ifCartHave2ProductsFromSameOfferThenWhenOneIsEliminatedThenBothAreDeleted() {
        Long commerceId1 = 1L;
        Long productId1 = 1L;
        Long productId2 = 2L;
        Long offerId1 = 1L;
        CartProduct cartOfferProduct1 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        CartProduct cartOfferProduct2 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId2).withOfferId(offerId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartOfferProduct1);
        shoppingCart.addProduct(cartOfferProduct2);

        shoppingCart.removeOfferById(offerId1);

        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
    }

    @Test
    public void addOfferExistentInShoppingCartShouldSumQuantities() {
        Long commerceId1 = 1L;
        Long productId1 = 1L;
        Long offerId1 = 1L;
        CartProduct cartOfferProduct1 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        CartProduct cartOfferProduct2 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();

        shoppingCart.addProduct(cartOfferProduct1);
        shoppingCart.addProduct(cartOfferProduct2);

        Assertions.assertEquals(2, shoppingCart.getCartProducts().get(0).getQuantity());
    }

    @Test
    public void ifAddOfferWhenAskByTotalAmountShouldReturnAmountWithDiscount() {
        Long commerceId1 = 1L;
        Long productId1 = 1L;
        Long offerId1 = 1L;
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withOfferId(offerId1).withProductId(productId1)
                .withPrice(100.0).withQuantity(1)
                .withOff(20)
                .build();

        shoppingCart.addProduct(cartProduct);

        Assertions.assertEquals(80.0, shoppingCart.getTotalAmount());
    }

    @Test
    public void ifAShoppingCartHasProductsAndOffersOf2CommercesThenWhenGeneratePurchaseShouldReturnAListWith2Purchases() {
        Long commerceId1 = 1L;
        Long commerceId2 = 2L;
        CartProduct cartProduct1 = CartProductBuilder.aProductCartBuilder()
                .anyProduct().withCommerceId(commerceId1).build();
        CartProduct cartProduct2 = CartProductBuilder.aProductCartBuilder()
                .anyProduct().withCommerceId(commerceId2).build();

        shoppingCart.addProduct(cartProduct1);
        shoppingCart.addProduct(cartProduct2);

        List<Purchase> purchases = shoppingCart.generatePurchase();

        Assertions.assertEquals(2, purchases.size());
        Assertions.assertEquals(commerceId1, purchases.get(0).getCommerceId());
        Assertions.assertEquals(commerceId2, purchases.get(1).getCommerceId());
        Assertions.assertFalse(purchases.get(1).getCartProducts().isEmpty());
    }

    @Test
    public void updateProductQuantity() {
        Long commerceId1 = 1L;
        Long productId1 = 1L;
        CartProduct cartProduct1 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartProduct1);

        shoppingCart.updateProductQuantity(productId1, 10);

        Assertions.assertEquals(10, shoppingCart.getCartProducts().get(0).getQuantity());
    }

    @Test
    public void updateOfferQuantity() {
        Long commerceId1 = 1L;
        Long productId1 = 1L;
        Long offerId1 = 1L;
        CartProduct cartProduct1 = CartProductBuilder.aProductCartBuilder().anyProduct()
                .withCommerceId(commerceId1).withProductId(productId1).withOfferId(offerId1)
                .withQuantity(1).build();
        shoppingCart.addProduct(cartProduct1);

        shoppingCart.updateOfferQuantity(productId1, 10);

        Assertions.assertEquals(10, shoppingCart.getCartProducts().get(0).getQuantity());
    }
}
