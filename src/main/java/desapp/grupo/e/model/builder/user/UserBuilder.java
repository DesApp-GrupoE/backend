package desapp.grupo.e.model.builder.user;

import desapp.grupo.e.model.user.User;

public class UserBuilder {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String secret;
    private boolean auth2fa;


    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public UserBuilder anyUser() {
        this.id = 1L;
        this.name = "User";
        this.surname = "Test";
        this.email = "test@test.test";
        this.password = "secret_password";
        this.secret = "secret";
        this.auth2fa = false;
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

    public UserBuilder withSecret(String secret) {
        this.secret = secret;
        return this;
    }

    public UserBuilder withAuth2fa(Boolean auth2fa) {
        this.auth2fa = auth2fa;
        return this;
    }

    public User build() {
        User user = new User(name, surname, email, password);
        user.setId(this.id);
        user.setSecret(this.secret);
        user.setAuth2fa(this.auth2fa);
        resetBuilder();
        return user;
    }

    public void resetBuilder() {
        this.id = null;
        this.name = null;
        this.surname = null;
        this.email = null;
        this.password = null;
        this.secret = null;
        this.auth2fa = false;
    }
}
