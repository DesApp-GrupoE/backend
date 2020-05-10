package desapp.grupo.e.model.builder;

import desapp.grupo.e.model.user.Commerce;

public class CommerceBuilder {

    private Long id;
    private String name;
    private String surname;
    private String password;
    private String email;

    public static CommerceBuilder aCommerce() {
        return new CommerceBuilder();
    }

    public CommerceBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommerceBuilder anyCommerce() {
        this.name = "Test";
        this.surname = "Test";
        this.email = "test@test.test";
        this.password = "secret_password";
        return this;
    }

    public Commerce build() {
        Commerce commerce = new Commerce(this.name, this.surname, this.email, this.password);
        commerce.setId(this.id);
        resetBuilder();
        return commerce;
    }

    private void resetBuilder() {
        this.id = null;
        this.name = null;
        this.surname = null;
        this.password = null;
        this.email = null;
    }
}
