package desapp.grupo.e.model.dto.category.alert;

import desapp.grupo.e.model.product.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.ArrayList;
import java.util.Set;

public class CategoryAlertDTOTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Test
    public void createCorrectCategoryAlert() {
        CategoryAlertDTO categoryAlertDTO = new CategoryAlertDTO();
        categoryAlertDTO.setCategory(Category.ALMACEN.name());
        categoryAlertDTO.setPercentage(10);

        Set<ConstraintViolation<CategoryAlertDTO>> violations = validator.validate(categoryAlertDTO);

        Assertions.assertTrue(violations.isEmpty());
    }

    @Test
    public void createCategoryWithoutFieldCategoryMandadory() {
        CategoryAlertDTO categoryAlertDTO = new CategoryAlertDTO();
        categoryAlertDTO.setPercentage(10);

        Set<ConstraintViolation<CategoryAlertDTO>> violations = validator.validate(categoryAlertDTO);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CategoryAlertDTO>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Category is mandatory", constraintViolations.get(0).getMessage());
    }

    @Test
    public void createCategoryWithoutFieldPercentageMandadory() {
        CategoryAlertDTO categoryAlertDTO = new CategoryAlertDTO();
        categoryAlertDTO.setCategory(Category.ALMACEN.name());

        Set<ConstraintViolation<CategoryAlertDTO>> violations = validator.validate(categoryAlertDTO);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CategoryAlertDTO>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Percentage is mandatory", constraintViolations.get(0).getMessage());
    }

    @Test
    public void createCategoryWithPercentageOutOfRange() {
        CategoryAlertDTO categoryAlertDTO = new CategoryAlertDTO();
        categoryAlertDTO.setCategory(Category.ALMACEN.name());
        categoryAlertDTO.setPercentage(101);

        Set<ConstraintViolation<CategoryAlertDTO>> violations = validator.validate(categoryAlertDTO);

        Assertions.assertFalse(violations.isEmpty());
        ArrayList<ConstraintViolation<CategoryAlertDTO>> constraintViolations = new ArrayList<>(violations);
        Assertions.assertEquals("Only permit percentage between 1 and 100", constraintViolations.get(0).getMessage());
    }
}
