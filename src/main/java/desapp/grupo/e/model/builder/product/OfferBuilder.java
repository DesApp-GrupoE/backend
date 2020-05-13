package desapp.grupo.e.model.builder.product;

import desapp.grupo.e.model.product.Offer;
import desapp.grupo.e.model.product.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OfferBuilder {

    private Long id;
    private String name;
    private Double off;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private List<Product> products;

    private OfferBuilder() {
        this.products = new ArrayList<>();
    }

    public static OfferBuilder aOffer() {
        return new OfferBuilder();
    }

    public OfferBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public OfferBuilder withOff(Double off) {
        this.off = off;
        return this;
    }

    public OfferBuilder withProduct(Product product) {
        this.products.add(product);
        return this;
    }

    public Offer build() {
        Offer offer = new Offer(this.name, this.off, this.dateFrom, this.dateTo);
        offer.setProducts(products);
        resetBuilder();
        return offer;
    }

    private void resetBuilder() {
        this.id = null;
        this.name = null;
        this.off = null;
        this.dateFrom = null;
        this.dateTo = null;
        this.products = new ArrayList<>();

    }

    public OfferBuilder anyOffer() {
        this.id = 1L;
        this.name = "Super Offer";
        this.off = 10.0;
        this.dateFrom = LocalDateTime.now();
        this.dateTo = LocalDateTime.now().plusDays(1);
        this.products = new ArrayList<>();
        return this;
    }
}