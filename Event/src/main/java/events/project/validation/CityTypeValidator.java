package events.project.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * Walidator dla typu wyliczeniowego City
 * @version 1.1
 */
public class CityTypeValidator implements ConstraintValidator<CityTypeConstraint, String> {

    private CityTypeConstraint annotation;

    @Override
    public void initialize(CityTypeConstraint annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString()))) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
