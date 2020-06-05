package desapp.grupo.e.model.builder.cart;

import desapp.grupo.e.model.cart.CartProduct;

public class CartProductBuilder {

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

    public static CartProductBuilder aProductCartBuilder() {
        return new CartProductBuilder();
    }
    
    public CartProductBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CartProductBuilder withProductId(Long productId) {
        this.productId = productId;
        return this;
    }

    public CartProductBuilder withCommerceId(Long commerceId) {
        this.commerceId = commerceId;
        return this;
    }

    public CartProductBuilder withOfferId(Long offerId) {
        this.offerId = offerId;
        return this;
    }

    public CartProductBuilder withName(String name) {
        this.name= name;
        return this;
    }

    public CartProductBuilder withBrand(String brand) {
        this.brand= brand;
        return this;
    }

    public CartProductBuilder withPrice(Double price) {
        this.price= price;
        return this;
    }

    public CartProductBuilder withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public CartProductBuilder withOff(Integer off) {
        this.off = off;
        return this;
    }

    public CartProductBuilder anyProduct() {
        this.id = 1L;
        this.productId = 1L;
        this.commerceId = 1L;
        this.name = "test";
        this.brand = "test";
        this.price = 10.0;
        this.quantity = 1;
        this.img = "urlImage.jpg";
        this.offerId = null;
        return this;
    }

    public CartProduct build() {
        CartProduct cartProduct = new CartProduct();
        cartProduct.setId(this.id);
        cartProduct.setProductId(this.productId);
        cartProduct.setCommerceId(this.commerceId);
        cartProduct.setOfferId(this.offerId);
        cartProduct.setBrand(this.brand);
        cartProduct.setImg(this.img);
        cartProduct.setName(this.name);
        cartProduct.setPrice(this.price);
        cartProduct.setQuantity(this.quantity);
        cartProduct.setOff(off);
        resetBuilder();
        return cartProduct;
    }

    public void resetBuilder() {
        this.id = null;
        this.productId = null;
        this.commerceId = null;
        this.brand = null;
        this.img = null;
        this.name = null;
        this.price = null;
        this.quantity = null;
    }

}
