package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.cart.CartProduct;

import java.util.ArrayList;
import java.util.List;

public class Purchase {

    private Long commerceId;
    private List<CartProduct> cartProducts;

    public Purchase(Long commerceId) {
        this.commerceId = commerceId;
        this.cartProducts = new ArrayList<>();
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Long getCommerceId() {
        return this.commerceId;
    }

    public void setCartProducts(List<CartProduct> productsCarts) {
        this.cartProducts = productsCarts;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

}
