package events.project.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<DateConstraint, LocalDate> {

    private DateConstraint annotation;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    public void initialize(DateConstraint annotation) {
        this.annotation=annotation;
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {

        boolean result = false;
            if(value!=null){
                result=true;
            }
        return result;
    }

}
