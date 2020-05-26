package desapp.grupo.e.model.builder.cart;

import desapp.grupo.e.model.cart.CartProduct;
import desapp.grupo.e.model.cart.CartOfferProduct;

import java.util.ArrayList;
import java.util.List;

public class CartOfferProductBuilder {

    private Long commerceId;
    private Long offerId;
    private Integer off;
    private Integer quantity;
    private List<CartProduct> cartProducts;

    private CartOfferProductBuilder() {
        this.cartProducts = new ArrayList<>();
    }

    public static CartOfferProductBuilder aCartOfferProductBuilder() {
        return new CartOfferProductBuilder();
    }

    public CartOfferProductBuilder anyCartOfferProduct() {
        this.commerceId = 1L;
        this.off = 10;
        this.offerId = 1L;
        this.quantity = 1;
        return this;
    }

    public CartOfferProduct build() {
        CartOfferProduct cartOfferProduct = new CartOfferProduct(this.commerceId, this.offerId, this.off);
        cartOfferProduct.setCartProducts(this.cartProducts);
        cartOfferProduct.setQuantity(this.quantity);
        return cartOfferProduct;
    }

    public CartOfferProductBuilder withOff(Integer off) {
        this.off = off;
        return this;
    }

    public CartOfferProductBuilder withCartProduct(CartProduct cartProduct) {
        this.cartProducts.add(cartProduct);
        return this;
    }

    public CartOfferProductBuilder withCommerceId(Long commerceId1) {
        this.commerceId = commerceId1;
        return this;
    }

    public CartOfferProductBuilder withOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public CartOfferProductBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }
}
