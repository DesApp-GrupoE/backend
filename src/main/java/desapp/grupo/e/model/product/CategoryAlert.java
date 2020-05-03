package desapp.grupo.e.model.product;

import javax.persistence.*;

@Entity
@Table(name = "alert_category")
public class CategoryAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="alert_category_id_seq")
    private Long id;
    @Column(name = "id_customer")
    private Long idCustomer;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Category category;
    @Column(nullable = false)
    private Integer percentage;

    public CategoryAlert() {
        // Dejo constructor vacio para el mapping de hibernate
    }

    public CategoryAlert(Category category, int percentage) {
        this.category = category;
        this.percentage = percentage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}

