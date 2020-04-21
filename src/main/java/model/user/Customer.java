package model.user;

public class Customer extends User {

    public Customer() {
        super("", "", "", "", Role.CUSTOMER);
    }

    public Customer(String user, String test, String s, String secret_password) {
        super(user, test, s, secret_password, Role.CUSTOMER);
    }

    public Role getRole() {
        return this.role;
    }
}
