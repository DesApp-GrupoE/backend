package desapp.grupo.e.model.dto.product;

import com.opencsv.bean.CsvBindByName;
import desapp.grupo.e.model.product.Product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ProductDTO {

    @CsvBindByName
    private Long id;
    @NotBlank(message = "Name is mandatory")
    @CsvBindByName(required = true)
    private String name;
    @NotBlank(message = "Brand is mandatory")
    @CsvBindByName(required = true)
    private String brand;
    @NotNull(message = "price is mandatory")
    @CsvBindByName(required = true)
    private Double price;
    @NotNull(message = "stock is mandatory")
    @CsvBindByName(required = true)
    private Integer stock;
    @NotBlank(message = "IMG is mandatory")
    @CsvBindByName(required = true)
    private String img;

    public ProductDTO() {
        // Constructor vacio para Jackson
    }

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.brand = product.getBrand();
        this.stock = product.getStock();
        this.img = product.getImg();
        this.price = product.getPrice();
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
