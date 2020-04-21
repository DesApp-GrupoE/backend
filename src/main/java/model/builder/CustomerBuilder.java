package model.builder;

import model.user.Customer;

public class CustomerBuilder {

    private Customer customer;

    public CustomerBuilder anyCustomer() {
        this.customer = new Customer("User", "Test", "test@test.com", "secret_password");
        return this;
    }

    public Customer build() {
        Customer customer = this.customer;
        this.customer = new Customer();
        return customer;
    }

}
