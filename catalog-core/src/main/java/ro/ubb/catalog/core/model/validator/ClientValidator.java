package ro.ubb.catalog.core.model.validator;

import org.springframework.stereotype.Component;
import ro.ubb.catalog.core.model.Client;

import java.time.LocalDate;

@Component
public class ClientValidator implements Validator<Client>{
    @Override
    public void validate(Client entity) throws ValidatorException {
        if(entity.getName().equals("")) throw new ValidatorException("Client without name!");
        if(entity.getEmail().equals("")) throw new ValidatorException("Client without email!");
        if(entity.getDateOfBirth().isBefore(LocalDate.of(1880, 1, 1))) throw new ValidatorException("Client too old");
        if(entity.getDateOfRegister().isBefore(LocalDate.of(2010, 1, 1))) throw new ValidatorException("Registration too old");
    }
}
