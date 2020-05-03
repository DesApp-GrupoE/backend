package desapp.grupo.e.model.user;

import desapp.grupo.e.model.dto.user.CustomerDTO;
import desapp.grupo.e.model.product.CategoryAlert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer extends User {

    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", referencedColumnName = "id")
    private List<CategoryAlert> categoryAlerts;

    public Customer() {
        super("", "", "", "", Role.CUSTOMER);
        this.categoryAlerts = new ArrayList<>();
    }

    public Customer(String user, String test, String email, String secret_password) {
        super(user, test, email, secret_password, Role.CUSTOMER);
        this.categoryAlerts = new ArrayList<>();
    }

    public Customer(CustomerDTO customerDTO) {
        this.name = customerDTO.getName();
        this.surname = customerDTO.getSurname();
        this.email = customerDTO.getEmail();
        this.password =  customerDTO.getPassword();
        this.role = Role.CUSTOMER;
        this.categoryAlerts = new ArrayList<>();
    }

    public Role getRole() {
        return this.role;
    }

    public List<CategoryAlert> getCategoryAlerts() {
        return categoryAlerts;
    }

    public void setCategoryAlerts(List<CategoryAlert> alertsCategory) {
        this.categoryAlerts = alertsCategory;
    }

    public void addCategoryAlert(CategoryAlert categoryAlert) {
        categoryAlert.setIdCustomer(this.id);
        this.categoryAlerts.add(categoryAlert);
    }

    public void removeCategoryAlert(CategoryAlert categoryAlert) {
        this.categoryAlerts.remove(categoryAlert);
    }
}
