package desapp.grupo.e.model.product;

import desapp.grupo.e.model.dto.product.ProductDTO;

import javax.persistence.*;


@Entity
@Table(name = "product")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String name;
    @Column(nullable = false)
    protected String brand;
    @Column(nullable = false)
    protected Double price;
    @Column(nullable = false)
    protected Integer stock;
    @Column(nullable = false)
    protected String img;
    @Column(name="commerce_id")
    private Long commerceId;

    public Product() {
        // Para el mapping de Hibernate
    }

    public Product(String name, String brand, Double price, Integer stock, String img, Long commerceId) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.img = img;
        this.commerceId = commerceId;
    }
 
    public Product(ProductDTO productDTO) {
        this.name = productDTO.getName();
        this.brand = productDTO.getBrand();
        this.price = productDTO.getPrice();
        this.stock =  productDTO.getStock();
        this.img = productDTO.getImg();
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


    public void setIdCommerce(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Long getIdCommerce() {
        return commerceId;
    }
}
