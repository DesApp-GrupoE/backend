package desapp.grupo.e.model.builder.purchase;

import desapp.grupo.e.model.purchase.DeliveryType;
import desapp.grupo.e.model.purchase.PurchaseTurn;

import java.time.LocalDateTime;

public class PurchaseTurnBuilder {

    private Long id;
    private Long idCommerce;
    private Long idUser;
    private Long idSubPurchase;
    private LocalDateTime dateTurn;
    private DeliveryType deliveryType;
    private String deliveryAddress;

    public static PurchaseTurnBuilder aPurchaseTurn() {
        return new PurchaseTurnBuilder();
    }

    public PurchaseTurnBuilder withIdCommerce(Long id) {
        this.idCommerce = id;
        return this;
    }

    public PurchaseTurnBuilder withDateTurn(LocalDateTime dateTurn) {
        this.dateTurn = dateTurn;
        return this;
    }

    public PurchaseTurnBuilder withDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public PurchaseTurnBuilder anyPurchaseTurn() {
        this.id = 1L;
        this.idCommerce = 1L;
        this.idUser = 1L;
        this.idSubPurchase = 1L;
        this.dateTurn = LocalDateTime.now();
        this.deliveryType = DeliveryType.ON_COMMERCE;
        return this;
    }

    public PurchaseTurn build() {
        PurchaseTurn purchaseTurn = new PurchaseTurn(this.idCommerce, this.dateTurn, this.deliveryType);
        purchaseTurn.setId(this.id);
        purchaseTurn.setDeliveryAddress(this.deliveryAddress);
        resetBuilder();
        return purchaseTurn;
    }

    private void resetBuilder() {
        this.id = null;
        this.idCommerce = null;
        this.idUser = null;
        this.idSubPurchase = null;
        this.dateTurn = null;
        this.deliveryType = null;
    }
}
