package com.gruenebohne.validator;

import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

	@Override
    public void validate(FacesContext context, UIComponent component,
            Object value) throws ValidatorException {
        String email = String.valueOf(value);
        boolean valid = true;
        if (value == null) {
            valid = false;
        } else if (!email.contains("@")) {
            valid = false;
        } else if (!email.contains(".")) {
            valid = false;
        } else if (email.contains(" ")) {
            valid = false;
        }
        if (!valid) {
            FacesMessage message = new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Invalid email address",
                    "...das ist aber keine E-Mail Adresse");
            throw new ValidatorException(message);
        }
    }
}
