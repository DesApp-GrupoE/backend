package desapp.grupo.e.model.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected double off;

    @Column(nullable = false)
    protected LocalDateTime dateFrom;

    @Column(nullable = false)
    protected LocalDateTime dateTo;

    @Transient
    private List<Product> products;

    public Offer() {
        // Constructor vacio para el mapping de Hibernate
        this.products = new ArrayList<>();
    }

    public Offer(String name, Double off, LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.name = name;
        this.off = off;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.products = new ArrayList<>();
    }

    public double applyOff(Product product) {
        return product.getPrice() - (product.getPrice() * this.off / 100);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return this.products;
    }

    public Double getTotalAmountWithDiscountApplied() {
        return this.products.stream().mapToDouble(this::applyOff).sum();
    }
}