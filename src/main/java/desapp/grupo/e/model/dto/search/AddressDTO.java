package desapp.grupo.e.model.dto.search;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonRootName(value = "address")
public class AddressDTO {

    @NotBlank(message = "Field 'address' is mandatory")
    private String address;
    @NotBlank(message = "Field 'addressNumber' is mandatory")
    private String addressNumber;
    @NotBlank(message = "Field 'location' is mandatory")
    private String location;
    @NotNull(message = "Field 'kilometers' is mandatory")
    private Integer kilometers;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressNumber() {
        return addressNumber;
    }

    public void setAddressNumber(String addressNumber) {
        this.addressNumber = addressNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }
}
