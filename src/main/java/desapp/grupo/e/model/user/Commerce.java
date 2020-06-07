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
    @JoinColumn(name = "id")
    private User user;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Long addressNumber;
    @Column(nullable = false)
    private String location;
    @Column(nullable = false)
    private Long phone;
    

    @Transient
    private List<PurchaseTurn> purchaseTurns;
    @Transient
    private List<Offer> offers;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "commerce_id")
    private List<Product> products;

    public Commerce() {
        this.purchaseTurns = new ArrayList<>();
    }

    public Commerce(String name, String address, Long addressNumber, String location, Long phone) {
        name = this.name;
        address = this.address;
        addressNumber = this.addressNumber;
        location = this.location;
        phone = this.phone;
        purchaseTurns = new ArrayList<>();
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

    public Long getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Long addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public Long getPhone(){
        return phone;
    }

    public void setPhone(Long phone){
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
}
