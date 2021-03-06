package desapp.grupo.e.model.dto.purchase;

import desapp.grupo.e.model.dto.cart.CartProductDTO;
import desapp.grupo.e.model.purchase.DeliveryType;

import java.util.List;


public class PurchaseDTO {

    private Long id;
    private Long commerceId;
    private String nameCommerce;
    private Long userId;
    private String date;
    private DeliveryType deliveryType;
    private Long turnId;
    private List<CartProductDTO> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public String getNameCommerce() {
        return nameCommerce;
    }

    public void setNameCommerce(String nameCommerce) {
        this.nameCommerce = nameCommerce;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getTurnId() {
        return turnId;
    }

    public void setTurnId(Long turnId) {
        this.turnId = turnId;
    }

    public List<CartProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<CartProductDTO> products) {
        this.products = products;
    }
}
