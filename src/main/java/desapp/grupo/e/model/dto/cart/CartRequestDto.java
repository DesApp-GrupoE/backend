package desapp.grupo.e.model.dto.cart;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartRequestDto {

    @NotNull(message = "Id is mandatory")
    private Long id;
    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must have a min value of 1")
    private Integer quantity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
