package desapp.grupo.e.model.dto.search;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "product")
public class ProductSearchDTO {

    private String name;
    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    public boolean isEmptyObject() {
        return name == null &&
                brand == null;
    }
}
