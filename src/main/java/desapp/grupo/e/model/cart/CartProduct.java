package desapp.grupo.e.model.cart;


public class CartProduct {

    private Long id;
    private Long productId;
    private Long commerceId;
    private Long offerId;
    private String name;
    private String brand;
    private String img;
    private Double price;
    private Integer quantity;
    private Integer off;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getCommerceId() {
        return commerceId;
    }

    public void setCommerceId(Long commerceId) {
        this.commerceId = commerceId;
    }

    public Long getOfferId() {
        return offerId;
    }

    public void setOfferId(Long offerId) {
        this.offerId = offerId;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Integer getOff() {
        return off;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    public Double calculateAmount() {
        if(this.offerId != null) {
            return (this.getPrice() - (this.getPrice() * this.getOff() / 100)) * this.getQuantity();
        } else {
            return this.getPrice() * this.getQuantity();
        }
    }

    public void addQuantity(Integer quantity) {
        this.quantity += quantity;
    }
}
