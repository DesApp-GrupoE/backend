package desapp.grupo.e.model.purchase;

import java.time.LocalDateTime;

public class PurchaseTurn {

    private Long id;
    private Long idCommerce;
    private Long idUser;
    private Long idSubPurchase;
    private LocalDateTime dateTurn;
    private DeliveryType deliveryType;
    private String deliveryAddress;

    public PurchaseTurn(Long idCommerce, LocalDateTime dateTurn, DeliveryType deliveryType) {
        this.idCommerce = idCommerce;
        this.dateTurn = dateTurn;
        this.deliveryType = deliveryType;
    }

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

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Long getIdSubPurchase() {
        return idSubPurchase;
    }

    public void setIdSubPurchase(Long idSubPurchase) {
        this.idSubPurchase = idSubPurchase;
    }

    public LocalDateTime getDateTurn() {
        return dateTurn;
    }

    public void setDateTurn(LocalDateTime dateTurn) {
        this.dateTurn = dateTurn;
    }

    public DeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
}
