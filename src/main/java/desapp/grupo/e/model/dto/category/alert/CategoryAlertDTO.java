package desapp.grupo.e.model.dto.category.alert;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class CategoryAlertDTO {

    @NotBlank(message = "Category is mandatory")
    private String category;
    @NotNull(message = "Percentage is mandatory")
    @Range(min = 1, max = 100, message = "Only permit percentage between 1 and 100")
    private Integer percentage;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
}
