package desapp.grupo.e.model.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class SearcherDTO {

    @Valid
    @NotNull(message = "Field 'address' is mandatory")
    @JsonProperty("address")
    private AddressDTO addressDTO;
    @JsonProperty("product")
    private ProductSearchDTO  productSearchDTO;

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public ProductSearchDTO getProductSearchDTO() {
        return productSearchDTO;
    }

    public void setProductSearchDTO(ProductSearchDTO productSearchDTO) {
        this.productSearchDTO = productSearchDTO;
    }
}
