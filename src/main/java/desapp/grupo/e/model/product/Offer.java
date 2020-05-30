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

    private Long idCommerce;

    @Column(nullable = false)
    protected String name;

    @Column(nullable = false)
    protected Integer off;

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

    public Offer(Long idCommerce, String name, Integer off, LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.idCommerce = idCommerce;
        this.name = name;
        this.off = off;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.products = new ArrayList<>();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getIdCommerce() {
        return this.idCommerce;
    }

    public void setIdCommerce(Long idCommerce) {
        this.idCommerce = idCommerce;
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

    public Integer getOff() {
        return off;
    }

    public void setOff(Integer off) {
        this.off = off;
    }

    public Double getTotalAmountWithDiscountApplied() {
        return this.products.stream().mapToDouble(this::applyOff).sum();
    }
}