package desapp.grupo.e.model.purchase;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class PurchaseTurn {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_id_purchase_turn")
    private Long id;
    @Column(nullable = false, name = "id_commerce")
    private Long idCommerce;
    @Column
    private Long idUser;
    @Column(nullable = false)
    private LocalDateTime dateTurn;
    @Column(nullable = false)
    private DeliveryType deliveryType;
    @Column
    private String deliveryAddress;

    public PurchaseTurn() {
        // contructor empty to mapping Hibernate
    }

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
