package desapp.grupo.e.model.builder.user;

import desapp.grupo.e.model.user.User;

public class UserBuilder {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;


    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder anyUser() {
        this.id = 1L;
        this.name = "User";
        this.surname = "Test";
        this.email = "test@test.test";
        this.password = "secret_password";
        return this;
    }

    public UserBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder withSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        User user = new User(name, surname, email, password);
        user.setId(this.id);
        resetBuilder();
        return user;
    }

    public void resetBuilder() {
        this.id = null;
        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
    }
}
