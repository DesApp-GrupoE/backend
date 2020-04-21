package model.user;

public abstract class User {

    protected Integer id;
    protected String name;
    protected String surname;
    protected String email;
    protected String password;
    protected Role role;

    public User(String name, String surname, String email, String password, Role role) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.role = role;
    }

}
