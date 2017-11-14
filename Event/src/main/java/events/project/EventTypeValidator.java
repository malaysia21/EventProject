package events.project;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EventTypeValidator implements ConstraintValidator<EventTypeConstraint, String> {

    private EventTypeConstraint annotation;

    @Override
    public void initialize(EventTypeConstraint annotation) {
        this.annotation=annotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(value.equals(enumValue.toString())
                        || (this.annotation.ignoreCase() && value.equalsIgnoreCase(enumValue.toString())))
                {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

}
