package chatbot.services;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Created by matthias6 on 09.03.2015.
 * Wrapper for validation framework. Existing for the sole purpose of making validation injectable via Spring annotation
 * config.
 */
@Component
public class ValidationService {

    private final ValidatorFactory validatorFactory;

    public ValidationService(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
    }

    public Validator getValidator(){
        return validatorFactory.getValidator();
    }

    public Set<ConstraintViolation<Object>> validate(Object toValidate){
        return getValidator().validate(toValidate);
    }

    public Set<ConstraintViolation<Object>> validate(Object toValidate, Class<?>... groups){
        return getValidator().validate(toValidate, groups);
    }
}
