package desapp.grupo.e.webservice.controller.purchase;

import desapp.grupo.e.model.purchase.DeliveryType;

import javax.validation.constraints.NotNull;

public class PurchaseRequestDTO {

    @NotNull
    private Long commerceId;
    @NotNull
    private DeliveryType deliveryType;
    private Long turnId;
    private String address;

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Long getTurnId() {
        return turnId;
    }

    public void setTurnId(Long turnId) {
        this.turnId = turnId;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
