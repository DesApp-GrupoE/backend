package desapp.grupo.e.model.builder;

import desapp.grupo.e.model.product.Product;

public class ProductBuilder {

    private Long idCommerce;
    private String name;
    private String brand;
    private Double price;
    private Integer stock;
    private String img;

    public static ProductBuilder aProduct() {
        return new ProductBuilder();
    }

    public ProductBuilder withIdCommerce(Long idCommerce) {
        this.idCommerce = idCommerce;
        return this;
    }

    public ProductBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder withBrand(String brand) {
        this.brand = brand;
        return this;
    }

    public ProductBuilder withPrice(Double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder withStock(Integer stock) {
        this.stock = stock;
        return this;
    }

    public ProductBuilder withImg(String urlImg) {
        this.img = urlImg;
        return this;
    }

    public ProductBuilder anyProduct() {
        this.idCommerce = 1L;
        this.name = "test";
        this.brand = "Test";
        this.price = 10.0;
        this.stock = 10;
        this.img = "urlImage.jpg";
        return this;
    }

    public Product build() {
        Product product = new Product(this.name, this.brand, this.price, this.stock, this.img, this.idCommerce);
        resetBuilder();
        return product;
    }

    private void resetBuilder() {
        this.idCommerce = null;
        this.name = null;
        this.brand = null;
        this.stock = null;
        this.price = null;
        this.img = null;
    }

}
