package desapp.grupo.e.model.product;

import java.time.LocalDateTime;
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

    public void applyOff() {
        // complete this method
    }
}