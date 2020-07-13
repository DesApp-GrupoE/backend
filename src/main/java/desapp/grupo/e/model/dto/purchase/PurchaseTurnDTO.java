package desapp.grupo.e.model.dto.purchase;

import desapp.grupo.e.model.purchase.DeliveryType;
import desapp.grupo.e.validator.LocalDateTimeFormat;

import javax.validation.constraints.NotNull;

public class PurchaseTurnDTO {

    private Long id;
    @LocalDateTimeFormat
    private String date;
    @NotNull
    private Long idCommerce;
    @NotNull
    private DeliveryType deliveryType;
    private Long idUser;
    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCommerce() {
        return idCommerce;
    }

    public void setIdCommerce(Long idCommerce) {
        this.idCommerce = idCommerce;
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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
