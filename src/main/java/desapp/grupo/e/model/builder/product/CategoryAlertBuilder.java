package desapp.grupo.e.model.builder.product;

import desapp.grupo.e.model.product.Category;
import desapp.grupo.e.model.product.CategoryAlert;

public class CategoryAlertBuilder {

    private Long id;
    private Category category;
    private Integer percentage;

    public static CategoryAlertBuilder aCategoryAlert() {
        return new CategoryAlertBuilder();
    }

    public CategoryAlertBuilder withId(Long id) {
        this.id = id;
        return this;
    }

    public CategoryAlertBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public CategoryAlertBuilder withPercentage(Integer percentage) {
        this.percentage = percentage;
        return this;
    }

    public CategoryAlertBuilder anyCategoryAlert() {
        this.id = 1L;
        this.category = Category.ALMACEN;
        this.percentage = 10;
        return this;
    }

    public CategoryAlert build() {
        CategoryAlert categoryAlert = new CategoryAlert(this.category, this.percentage);
        categoryAlert.setId(this.id);
        resetBuilder();
        return categoryAlert;
    }

    public void resetBuilder() {
        this.id = null;
        this.category = null;
        this.percentage = null;
    }
}
