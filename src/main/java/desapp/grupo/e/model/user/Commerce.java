package desapp.grupo.e.model.user;

import desapp.grupo.e.model.purchase.PurchaseTurn;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.product.Offer;
import javax.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "commerce")
public class Commerce {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="seq_id_commerce")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phone;
    @Column
    private Double latitude;
    @Column
    private Double longitude;
    @Column(nullable = false)
    private Boolean doDelivery;
    @Column
    private Double deliveryUp;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_commerce")
    private List<PurchaseTurn> purchaseTurns;
    @Transient
    private List<Offer> offers;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private List<Product> products;
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "CommerceSector", joinColumns = @JoinColumn(name = "commerce_id"))
    @Enumerated(EnumType.STRING)
    private List<CommerceSector> sectors;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private List<CommerceHour> hours;

    public Commerce() {
        this.purchaseTurns = new ArrayList<>();
        this.products = new ArrayList<>();
        this.sectors = new ArrayList<>();
        this.hours = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<PurchaseTurn> getPurchaseTurns() {
        return purchaseTurns;
    }

    public void setPurchaseTurns(List<PurchaseTurn> purchaseTurns) {
        this.purchaseTurns = purchaseTurns;
    }

    public void addPurchaseTurn(PurchaseTurn purchaseTurn) {
        if(this.purchaseTurns.stream().noneMatch(turn -> turn.getDateTurn().equals(purchaseTurn.getDateTurn()))) {
            this.purchaseTurns.add(purchaseTurn);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public List<Offer> getOffers() {
        return offers;
    }    

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    public List<Product> getProducts() {
        return products;
    }    

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void removePurchaseTurn(PurchaseTurn purchaseTurn) {
        this.purchaseTurns.remove(purchaseTurn);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setSectors(List<CommerceSector> sectors) {
        this.sectors = sectors;
    }

    public List<CommerceSector> getSectors() {
        return sectors;
    }

    public void setHours(List<CommerceHour> hours) {
        this.hours = hours;
    }

    public List<CommerceHour> getHours() {
        return hours;
    }

    public Boolean getDoDelivery() {
        return doDelivery;
    }

    public void setDoDelivery(Boolean doDelivery) {
        this.doDelivery = doDelivery;
    }

    public Double getDeliveryUp() {
        return deliveryUp;
    }

    public void setDeliveryUp(Double deliveryUp) {
        this.deliveryUp = deliveryUp;
    }
}
