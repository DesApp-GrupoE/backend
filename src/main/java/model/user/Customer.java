package model.user;

public class Customer {

    private UserRol role;

    public Customer() {
        this.role = UserRol.CUSTOMER;
    }

    public UserRol getRole() {
        return this.role;
    }
}
