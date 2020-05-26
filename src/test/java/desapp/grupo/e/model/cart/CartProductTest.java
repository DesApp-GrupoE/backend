package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.builder.cart.CartProductBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartProductTest {

    @Test
    public void whenAskCalculateAmountToCartProductShouldReturnItsPriceByQuantity() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().withPrice(100.0).withQuantity(2).build();

        Assertions.assertEquals(200.0, cartProduct.calculateAmount());
    }

    @Test
    public void sumQuantityToCartProduct() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().withQuantity(1).build();

        cartProduct.addQuantity(2);

        Assertions.assertEquals(3, cartProduct.getQuantity());
    }
}
