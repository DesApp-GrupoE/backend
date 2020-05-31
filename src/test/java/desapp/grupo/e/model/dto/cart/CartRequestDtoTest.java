package desapp.grupo.e.model.dto.cart;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

public class CartRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void createCorrectCartRequest() {
        CartRequestDto cartRequest = new CartRequestDto();
        cartRequest.setId(1L);
        cartRequest.setQuantity(1);

        Set<ConstraintViolation<CartRequestDto>> violations = validator.validate(cartRequest);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void cartRequestWithIdNull() {
        CartRequestDto cartRequest = new CartRequestDto();
        cartRequest.setQuantity(1);

        Set<ConstraintViolation<CartRequestDto>> violations = validator.validate(cartRequest);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CartRequestDto>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Id is mandatory", constraintViolations.get(0).getMessage());
    }

    @Test
    public void cartRequestWithQuantityNull() {
        CartRequestDto cartRequest = new CartRequestDto();
        cartRequest.setId(1L);

        Set<ConstraintViolation<CartRequestDto>> violations = validator.validate(cartRequest);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CartRequestDto>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Quantity is mandatory", constraintViolations.get(0).getMessage());
    }

    @Test
    public void cartRequestWithQuantityLessThan1() {
        CartRequestDto cartRequest = new CartRequestDto();
        cartRequest.setId(1L);
        cartRequest.setQuantity(0);

        Set<ConstraintViolation<CartRequestDto>> violations = validator.validate(cartRequest);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CartRequestDto>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Quantity must have a min value of 1", constraintViolations.get(0).getMessage());
    }
}
