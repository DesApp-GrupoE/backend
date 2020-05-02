package desapp.grupo.e.model.user;

import desapp.grupo.e.model.dto.user.CustomerDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {

    public Customer() {
        super("", "", "", "", Role.CUSTOMER);
    }

    public Customer(String user, String test, String s, String secret_password) {
        super(user, test, s, secret_password, Role.CUSTOMER);
    }

    public Customer(CustomerDTO customerDTO) {
        this.name = customerDTO.getName();
        this.surname = customerDTO.getSurname();
        this.email = customerDTO.getEmail();
        this.password =  customerDTO.getPassword();
        this.role = Role.CUSTOMER;
    }

    public Role getRole() {
        return this.role;
    }
}
