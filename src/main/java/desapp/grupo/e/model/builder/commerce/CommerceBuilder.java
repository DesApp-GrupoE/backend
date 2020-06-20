package desapp.grupo.e.model.builder.commerce;

import desapp.grupo.e.model.user.Commerce;

public class CommerceBuilder {

    private Long id;
    private String name;
    private String address;
    private Long addressNumber;
    private String location;
    private String phone;
    private Double latitude;
    private Double longitude;

    public static CommerceBuilder aCommerce() {
        return new CommerceBuilder();
    }

    public CommerceBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CommerceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CommerceBuilder withAddress(String address) {
        this.address = address;
        return this;
    }

    public CommerceBuilder withAddressNumber(Long addressNumber) {
        this.addressNumber = addressNumber;
        return this;
    }

    public CommerceBuilder withLocation(String location) {
        this.location = location;
        return this;
    }

    public CommerceBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public CommerceBuilder withLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public CommerceBuilder withLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public CommerceBuilder anyCommerce() {
        this.name = "Test";
        this.address = "Brandsen";
        this.addressNumber = 300L;
        this.location = "Quilmes";
        this.phone = "1155443322";
        this.latitude = 0.0;
        this.longitude = 0.0;
        return this;
    }

    public Commerce build() {
        Commerce commerce = new Commerce();
        commerce.setId(this.id);
        commerce.setName(this.name);
        commerce.setAddress(this.address);
        commerce.setAddressNumber(this.addressNumber);
        commerce.setLocation(this.location);
        commerce.setPhone(this.phone);
        commerce.setLatitude(this.latitude);
        commerce.setLongitude(this.longitude);
        resetBuilder();
        return commerce;
    }

    private void resetBuilder() {
        this.id = null;
        this.name = null;
    }
}
