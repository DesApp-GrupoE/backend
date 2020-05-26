package desapp.grupo.e.model.cart;

import desapp.grupo.e.model.builder.cart.CartOfferProductBuilder;
import desapp.grupo.e.model.builder.cart.CartProductBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CartOfferProductTest {

    @Test
    public void addQuantityToCartOfferProduct() {
        CartOfferProduct cartOfferProduct = CartOfferProductBuilder.aCartOfferProductBuilder().withQuantity(1).build();

        cartOfferProduct.addQuantity(2);

        Assertions.assertEquals(3, cartOfferProduct.getQuantity());
    }

    @Test
    public void getCalculatedAmountOfCartOfferProduct() {
        CartProduct cartProduct = CartProductBuilder.aProductCartBuilder().withPrice(100.0).withQuantity(1).build();
        CartOfferProduct cartOfferProduct = CartOfferProductBuilder.aCartOfferProductBuilder()
                .withCartProduct(cartProduct)
                .withOff(20)
                .withQuantity(5).build();

        Assertions.assertEquals(400, cartOfferProduct.calculateTotalAmount());
    }
}
