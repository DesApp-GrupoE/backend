package desapp.grupo.e.model.dto.product;

import desapp.grupo.e.model.product.Product;

import javax.validation.constraints.NotBlank;

public class ProductDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Price is mandatory")
    private float price;

    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Stock is mandatory")
    private int stock;

    @NotBlank(message = "IMG is mandatory")
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
