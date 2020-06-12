package desapp.grupo.e.model.dto.search;

import com.fasterxml.jackson.annotation.JsonRootName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonRootName(value = "address")
public class AddressDTO {

    @NotBlank(message = "Field 'county' is mandatory")
    private String county;
    @NotBlank(message = "Field 'region' is mandatory")
    private String region;
    @NotNull(message = "Field 'kilometers' is mandatory")
    private Integer kilometers;
    private Double latitude;
    private Double longitude;

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
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

    public Boolean hasCoordenates() {
        return this.latitude != null && this.latitude != 0 &&
                this.longitude != null && this.longitude != 0;
    }
}
