package desapp.grupo.e.model.product;

import desapp.grupo.e.model.dto.product.ProductDTO;
import desapp.grupo.e.model.user.Merchant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    protected float price;
    @Column(nullable = false)
    protected int stock;
    @Column(nullable = false)
    protected String img;
    @Enumerated(EnumType.STRING)
    protected Merchant merchant;



    public Product(String name, String brand, float price, int stock, String img, Merchant merchant) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.stock = stock;
        this.img = img;
        this.merchant = merchant;
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

    public Merchant gMerchant() {
        return merchant;
    }

    public void setMerchant(Merchant merchant) {
        this.merchant = merchant;
    }




}