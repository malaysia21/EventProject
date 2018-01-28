package events.project.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

/**
 * Constraint dla walidatora {@link CityTypeValidator}
 * @version 1.1
 */
@Documented
@Constraint(validatedBy = CityTypeValidator.class)
@Target({ METHOD, FIELD, CONSTRUCTOR, PARAMETER, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)

public @interface CityTypeConstraint {
    String message() default "Invalid type of city";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<? extends Enum<?>> enumClass();
    boolean ignoreCase() default false;

}