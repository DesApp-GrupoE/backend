package desapp.grupo.e.model.dto.commerce;

import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.user.CommerceSector;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

public class CommerceDTO {

    private Long id;
    @NotNull(message = "name.notNull")
    private String name;
    @NotNull(message = "address.notNull")
    private String address;
    @NotNull(message = "latitude.notNull")
    private Double latitude;
    @NotNull(message = "longitude.notNull")
    private Double longitude;
    private boolean doDelivery;
    private Double deliveryUp;
    @NotNull(message = "phone.notNull")
    private String phone;
    @NotEmpty(message = "sectors.notEmpty")
    private List<CommerceSector> sectors;
    @NotEmpty(message = "hours.notEmpty")
    @Valid
    private List<CommerceHourDTO> hours;

    public CommerceDTO() {
        // Constructor vacio para Jackson
    }

    public CommerceDTO(Commerce commerce) {
        this.id = commerce.getId();
        this.name = commerce.getName();
        this.address = commerce.getAddress();
        this.phone = commerce.getPhone();
        this.sectors = commerce.getSectors();
        this.latitude = commerce.getLatitude();
        this.longitude = commerce.getLongitude();
        this.hours = commerce.getHours().stream()
                .map(CommerceHourDTO::new)
                .collect(Collectors.toList());
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

    public List<CommerceSector> getSectors() {
        return sectors;
    }

    public void setSectors(List<CommerceSector> sectors) {
        this.sectors = sectors;
    }

    public List<CommerceHourDTO> getHours() {
        return hours;
    }

    public void setHours(List<CommerceHourDTO> hours) {
        this.hours = hours;
    }

    public boolean getDoDelivery() {
        return doDelivery;
    }

    public void setDoDelivery(boolean doDelivery) {
        this.doDelivery = doDelivery;
    }

    public Double getDeliveryUp() {
        return deliveryUp;
    }

    public void setDeliveryUp(Double deliveryUp) {
        this.deliveryUp = deliveryUp;
    }
}
