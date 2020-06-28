package desapp.grupo.e.model.user;

import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.product.CategoryAlert;
import desapp.grupo.e.model.purchase.Purchase;
import javax.persistence.*;
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
    @Column
    private String secret;
    @Column(nullable = false)
    private Boolean auth2fa;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private List<CategoryAlert> categoryAlerts;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, optional = false)
    private Commerce commerce;
    @Transient
    private List<Purchase> purchases;
    private Boolean tokenActivated;

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
        this.auth2fa = userDTO.getAuth2fa();
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

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Boolean getAuth2fa() {
        return auth2fa;
    }

    public void setAuth2fa(Boolean auth2fa) {
        this.auth2fa = auth2fa;
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
        if(commerce == null) {
            // Si el mapeo no me trae un commerce, entonces debo nullear nuestro commerce para evitar inconsistencias
            this.commerce = null;
        } else {
            // Si el commerce != null guardo la instancia del user. Asi generamos el mapeo
            commerce.setUser(this);
        }
        this.commerce = commerce;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

}
