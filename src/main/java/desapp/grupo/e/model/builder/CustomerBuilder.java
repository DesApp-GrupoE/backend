package desapp.grupo.e.model.builder;

import desapp.grupo.e.model.user.Customer;

public class CustomerBuilder {

    private String name;
    private String surname;
    private String email;
    private String password;


    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    public CustomerBuilder anyCustomer() {
        this.name = "Customer";
        this.surname = "Test";
        this.email = "test@test.test";
        this.password = "secret_password";
        return this;
    }

    public CustomerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CustomerBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public CustomerBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public Customer build() {
        Customer customer = new Customer(name, surname, email, password);
        resetBuilder();
        return customer;
    }

    public void resetBuilder() {
        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
    }
}
