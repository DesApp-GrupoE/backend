package desapp.grupo.e.model.dto.commerce;
import desapp.grupo.e.model.user.Commerce;
import desapp.grupo.e.model.purchase.PurchaseTurn;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class CommerceDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;
    private String address;
    private Long addressNumber;
    private String location;
    private Long phone;
    private List<PurchaseTurn> purchaseTurns;

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
        this.purchaseTurns = commerce.getPurchaseTurns();
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

    public Long getPhone(){
        return phone;
    }

    public void setPhone(Long phone){
        this.phone = phone;
    }

    public List<PurchaseTurn> getPurchaseTurns() {
        return purchaseTurns;
    }

    public void setPurcharseTurns(List<PurchaseTurn> purchaseTurns) {
        this.purchaseTurns = purchaseTurns;
    }
}
