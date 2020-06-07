package desapp.grupo.e.model.builder.product;
import desapp.grupo.e.model.product.Product;


public class ProductBuilder {
    private String name;
    private String brand;
    private Double price;
    private Integer stock;
    private String img;
    private Long id;
    private Long commerceId;

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withName(String name) {
        this.name= name;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand= brand;
        return this;
    }

    public ProductBuilder withPrice(Double price) {
        this.price= price;
        return this;
    }

    public ProductBuilder withStock(int stock) {
        this.stock= stock;
        return this;
    }

    public ProductBuilder withId(Long productId) {
        this.id = productId;
        return this;
    }

    public ProductBuilder withImg(String img) {
        this.img = img;
        return this;
    }

    public ProductBuilder withCommerceId(Long commerceId) {
        this.commerceId = commerceId;
        return this;
    }

    public ProductBuilder anyProduct() {
        this.id = 1L;
        this.name = "test";
        this.brand = "test";
        this.price = 10.0;
        this.stock = 50;
        this.img = "urlImage.jpg";
        this.commerceId = 1L;
        return this;
    }

    public Product build() {
        Product product = new Product(this.name, this.brand, this.price, 
                                      this.stock, this.img, this.commerceId);
        product.setId(this.id);
        resetBuilder();
        return product;
    }

    public void resetBuilder() {
        this.name = null;
        this.brand = null;
        this.price = null;
        this.stock = null;
        this.img = null;
        this.id = null;
        this.commerceId = null;
    }

}
