package desapp.grupo.e.model.user;

import desapp.grupo.e.model.dto.user.UserDTO;
import desapp.grupo.e.model.product.CategoryAlert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private List<CategoryAlert> categoryAlerts;

    public User() {
        // Para el mapping de hibernate
        this.categoryAlerts = new ArrayList<>();
    }

    public User(String name, String surname, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.categoryAlerts = new ArrayList<>();
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
        categoryAlert.setIdUser(this.id);
        this.categoryAlerts.add(categoryAlert);
    }

    public void removeCategoryAlert(CategoryAlert categoryAlert) {
        this.categoryAlerts.remove(categoryAlert);
    }
}
