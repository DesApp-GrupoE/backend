package desapp.grupo.e.model.cart;

import java.util.ArrayList;
import java.util.List;

public class CartOfferProduct {

    private Long id;
    private Long commerceId;
    private Long offerId;
    private Integer off;
    private Integer quantity;
    private List<CartProduct> cartProducts;

    public CartOfferProduct(Long commerceId, Long offerId, Integer off) {
        this.commerceId = commerceId;
        this.offerId = offerId;
        this.off = off;
        this.cartProducts = new ArrayList<>();
    }

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Integer getOff() {
        return off;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double calculateTotalAmount() {
        double productsAmount = this.getCartProducts().stream().mapToDouble(CartProduct::calculateAmount).sum();
        return (productsAmount - (productsAmount * this.getOff() / 100)) * this.getQuantity();
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
