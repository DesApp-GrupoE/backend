package desapp.grupo.e.model.dto.commerce;

import desapp.grupo.e.model.user.Commerce;

import javax.validation.constraints.NotNull;

public class CommerceDTO {

    private Long id;
    @NotNull(message = "name.notNull")
    private String name;
    private String address;
    private Long addressNumber;
    private String location;
    @NotNull(message = "location.notNull")
    private Double latitude;
    @NotNull(message = "longitude.notNull")
    private Double longitude;
    @NotNull(message = "phone.notNull")
    private String phone;

    public CommerceDTO() {
        // Constructor vacio para Jackson
    }

    public CommerceDTO(Commerce commerce) {
        this.id = commerce.getId();
        this.name = commerce.getName();
        this.address = commerce.getAddress();
        this.addressNumber = commerce.getAddressNumber();
        this.location = commerce.getLocation();
        this.phone = commerce.getPhone();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public Long getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(Long addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
