package events.project.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

/**
 * Klasa przedstawiająca błędy walidatorów
 * @version 1.1
 */
public class ValidationErrorBuilder {
    public static ValidationError fromBindingErrors(Errors errors) {
        ValidationError error = new ValidationError("Validation failed. " + errors.getErrorCount() + " error(s)");
        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}