package desapp.grupo.e.model.user;

import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.exception.BusinessException;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;
import desapp.grupo.e.model.purchase.Purchase;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_db")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="user_id_seq")
    protected Long id;
    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String surname;
    @Column(nullable = false)
    protected String email;
    @Column(nullable = false)
    protected String password;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private List<CategoryAlert> categoryAlerts;
    @Transient
    private Commerce commerce;
    @Transient
    private Purchase currentPurchase;
    @Transient
    private List<Purchase> purchases;

    public User() {
        // Para el mapping de hibernate
        this.categoryAlerts = new ArrayList<>();
        this.purchases = new ArrayList<>();
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.categoryAlerts = new ArrayList<>();
        this.purchases = new ArrayList<>();
    }

    public User(UserDTO userDTO) {
        this.name = userDTO.getName();
        this.surname = userDTO.getSurname();
        this.email = userDTO.getEmail();
        this.password = userDTO.getPassword();
        this.categoryAlerts = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<CategoryAlert> getCategoryAlerts() {
        return categoryAlerts;
    }

    public void setCategoryAlerts(List<CategoryAlert> alertsCategory) {
        this.categoryAlerts = alertsCategory;
    }

    public void addCategoryAlert(CategoryAlert categoryAlert) {
        this.categoryAlerts.add(categoryAlert);
    }

    public void removeCategoryAlert(CategoryAlert categoryAlert) {
        this.categoryAlerts.remove(categoryAlert);
    }

    public Commerce getCommerce() {
        return commerce;
    }

    public void setCommerce(Commerce commerce) {
        this.commerce = commerce;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public Purchase getCurrentPurchase() {
        return currentPurchase;
    }

    public void setCurrentPurchase(Purchase currentPurchase) {
        this.currentPurchase = currentPurchase;
    }

    public void addProduct(Product product) {
        this.initCurrentPurchase();
        this.currentPurchase.addProduct(product);
    }

    public void removeProduct(Product product) {
        this.initCurrentPurchase();
        this.currentPurchase.removeProduct(product);
    }

    private void initCurrentPurchase() {
        if(this.currentPurchase == null) {
            this.currentPurchase = new Purchase();
            this.currentPurchase.setIdUser(this.id);
        }
    }

    public void finalizePurchase(LocalDateTime purchaseDate) throws BusinessException {
        this.initCurrentPurchase();
        if(this.currentPurchase.getSubPurchases().isEmpty()) {
            throw new BusinessException("A user can't finalize a purchase without products");
        }
        this.currentPurchase.setDatePurchase(purchaseDate);
        this.purchases.add(this.currentPurchase);
        this.currentPurchase = null;
    }

    public void addOffer(Offer offer) {
        this.initCurrentPurchase();
        this.currentPurchase.addOffer(offer);
    }

    public void removeOffer(Offer offer) {
        this.initCurrentPurchase();
        this.currentPurchase.removeOffer(offer);
    }
}
