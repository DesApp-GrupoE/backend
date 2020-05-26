package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.builder.cart.CartProductBuilder;
import desapp.grupo.e.model.builder.cart.CartOfferProductBuilder;
import desapp.grupo.e.model.purchase.Purchase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ShoppingCartTest {

    private static final Long userId = 1L;
    private ShoppingCart shoppingCart;

    @BeforeEach
    public void setUp() {
        this.shoppingCart = new ShoppingCart(userId);
    }

    @Test
    public void aNewShoppingCartShouldHasEmptyListOfCartProductAndCartOfferProduct() {
        Assertions.assertTrue(shoppingCart.getCartProducts().isEmpty());
        Assertions.assertTrue(shoppingCart.getCartOfferProducts().isEmpty());
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
    public void addOffer() {
        CartOfferProduct cartProductOffer = CartOfferProductBuilder.aCartOfferProductBuilder()
                .anyCartOfferProduct()
                .build();

        shoppingCart.addOffer(cartProductOffer);

        Assertions.assertEquals(1, shoppingCart.getCartOfferProducts().size());
    }

    @Test
    public void removeOffer() {
        CartOfferProduct cartProductOffer = CartOfferProductBuilder.aCartOfferProductBuilder()
                .anyCartOfferProduct()
                .build();
        shoppingCart.addOffer(cartProductOffer);

        shoppingCart.removeOffer(cartProductOffer);

        Assertions.assertTrue(shoppingCart.getCartOfferProducts().isEmpty());
    }

    @Test
    public void addOfferExistentInShoppingCartShouldSumQuantities() {
        Long commerceId = 1L;
        Long offerId = 1L;
        CartOfferProduct cartProductOffer1 = CartOfferProductBuilder.aCartOfferProductBuilder()
                .withCommerceId(commerceId).withOfferId(offerId)
                .withQuantity(1)
                .build();
        CartOfferProduct cartProductOffer2 = CartOfferProductBuilder.aCartOfferProductBuilder()
                .withCommerceId(commerceId).withOfferId(offerId)
                .withQuantity(2)
                .build();
        shoppingCart.addOffer(cartProductOffer1);
        shoppingCart.addOffer(cartProductOffer2);

        Assertions.assertEquals(1, shoppingCart.getCartOfferProducts().size());
        Assertions.assertEquals(3, shoppingCart.getCartOfferProducts().get(0).getQuantity());
    }

    @Test
    public void ifAddOfferWhenAskByTotalAmountShouldReturnAmountWithDiscount() {
        Long commerceId1 = 1L;
        CartOfferProduct cartProductOffer = CartOfferProductBuilder.aCartOfferProductBuilder()
                .anyCartOfferProduct()
                .withOff(20)
                .withCartProduct(
                    CartProductBuilder.aProductCartBuilder().anyProduct()
                        .withCommerceId(commerceId1).withPrice(100.0).withQuantity(1).build()
                )
                .build();

        shoppingCart.addOffer(cartProductOffer);

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
        CartOfferProduct cartProductOffer = CartOfferProductBuilder.aCartOfferProductBuilder()
                .anyCartOfferProduct()
                .withCommerceId(commerceId2)
                .withOff(20)
                .withCartProduct(
                        CartProductBuilder.aProductCartBuilder().anyProduct()
                                .withCommerceId(commerceId2).withPrice(100.0).withQuantity(1).build()
                )
                .build();

        shoppingCart.addProduct(cartProduct1);
        shoppingCart.addProduct(cartProduct2);
        shoppingCart.addOffer(cartProductOffer);

        List<Purchase> purchases = shoppingCart.generatePurchase();

        Assertions.assertEquals(2, purchases.size());
        Assertions.assertEquals(commerceId1, purchases.get(0).getCommerceId());
        Assertions.assertEquals(commerceId2, purchases.get(1).getCommerceId());
        Assertions.assertFalse(purchases.get(1).getCartProducts().isEmpty());
        Assertions.assertFalse(purchases.get(1).getCartOfferProducts().isEmpty());
    }

}
