package desapp.grupo.e.model.builder;

import desapp.grupo.e.model.user.Merchant;

public class MerchantBuilder {

    private String name;
    private String surname;
    private String password;
    private String email;

    public static MerchantBuilder aMerchant() {
        return new MerchantBuilder();
    }

    public MerchantBuilder anyMerchant() {
        this.name = "Test";
        this.surname = "Test";
        this.email = "test@test.test";
        this.password = "secret_password";
        return this;
    }

    public Merchant build() {
        Merchant merchant = new Merchant(this.name, this.surname, this.email, this.password);
        resetBuilder();
        return merchant;
    }

    private void resetBuilder() {
        this.name = null;
        this.surname = null;
        this.password = null;
        this.email = null;
    }
}
