package ro.ubb.catalog.core.model.validator;
import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Rental;

import java.time.LocalDate;

@Component
public class RentalValidator implements Validator<Rental> {
    @Override
    public void validate(Rental entity) throws ValidatorException {
        if (entity.getDateRented().isBefore(LocalDate.of(2010, 1, 1)))
            throw new ValidatorException("Cannot be rented before the app even started!");
        if (entity.getDateDue().isAfter(LocalDate.now().plusYears(3)))
            throw new ValidatorException("Movie can be rented up to maximum 3 years in the future!");
        if (entity.getDateDue().isBefore(entity.getDateRented()))
            throw new ValidatorException("Cannot have date due before date rented!");
    }
}
