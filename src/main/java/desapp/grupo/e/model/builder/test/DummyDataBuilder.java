package desapp.grupo.e.model.builder.test;

import desapp.grupo.e.model.test.DummyData;

public class DummyDataBuilder {

    private String email;
    private String password;
    private String address;

    public static DummyDataBuilder aDummyData() {
        return new DummyDataBuilder();
    }

    public DummyDataBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public DummyDataBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public DummyDataBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public DummyData build() {
        DummyData dummyData = new DummyData();
        dummyData.setEmail(this.email);
        dummyData.setAddress(this.address);
        dummyData.setPassword(this.password);
        resetBuilder();
        return dummyData;
    }

    private void resetBuilder() {
        this.email = null;
        this.password = null;
        this.address = null;
    }
}
