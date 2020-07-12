package desapp.grupo.e.model.purchase;

import desapp.grupo.e.model.cart.CartProduct;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="purchase_id_seq")
    private Long id;
    @Column(nullable = false)
    private Long commerceId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime date;
    @Column
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;
    @Column
    private Long turnId;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id")
    private List<CartProduct> cartProducts;

    public Purchase() {
        // Vacio para el mapping de Hibernate
    }

    public Purchase(Long commerceId) {
        this.commerceId = commerceId;
        this.cartProducts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Long getCommerceId() {
        return this.commerceId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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

    public void setCartProducts(List<CartProduct> productsCarts) {
        this.cartProducts = productsCarts;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

}
