package desapp.grupo.e.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFormatValidator implements ConstraintValidator<LocalDateTimeFormat, String> {

    private Boolean isOptional;
    private DateTimeFormatter dateTimeFormatter;

    @Override
    public void initialize(LocalDateTimeFormat dateFormat) {
        this.isOptional = dateFormat.optional();
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat.format());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        Boolean isValidDate = isValidDate(value);
        return this.isOptional ? (isValidDate || isNullOrEmpty(value)) : isValidDate;
    }

    private Boolean isValidDate(String value) {
        try {
            LocalDateTime.parse(value, this.dateTimeFormatter);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.equals("");
    }
}
