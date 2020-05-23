package desapp.grupo.e.model.product;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name = "alert_category")
public class CategoryAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator="alert_category_id_seq")
    private Long id;
    @Column(name = "category", nullable = false)
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

