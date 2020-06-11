package desapp.grupo.e.model.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PositionStack {

    private Double latitude;
    private Double longitude;
    private String type;
    private String name;
    private String number;
    @JsonProperty("postal_code")
    private String postalPode;
    private String street;
    private Integer confidence;
    private String region;
    @JsonProperty("region_code")
    private String regionCode;
    private String county;
    private String locality;
    private String administrative_area;
    private String neighbourhood;
    private String country;
    @JsonProperty("country_code")
    private String countryCode;
    private String continent;
    private String label;

    public PositionStack() {
        // Para el mapping de Jackson
    }

    public PositionStack(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalPode() {
        return postalPode;
    }

    public void setPostalPode(String postalPode) {
        this.postalPode = postalPode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getAdministrative_area() {
        return administrative_area;
    }

    public void setAdministrative_area(String administrative_area) {
        this.administrative_area = administrative_area;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
