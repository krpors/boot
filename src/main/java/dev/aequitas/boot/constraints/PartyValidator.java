package dev.aequitas.boot.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PartyValidator implements ConstraintValidator<PartyConstraint, String> {
    @Override
    public void initialize(PartyConstraint partyConstraint) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (s == null) {
            // whether a party can be null must be asserted with @NotNull.
            return true;
        }

        if (s.startsWith("CLP") || s.startsWith("RGB")) {
            String rest = s.substring(3);
            if (rest.equals("")) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "No party identifier given after (CLP|RGB)").addConstraintViolation();
                return false;
            }

            if (rest.matches("[0-9]+")) {
                return true;
            } else {
                constraintValidatorContext.buildConstraintViolationWithTemplate("Number expected after CLP|RGB").addConstraintViolation();
                return false;
            }
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate("CLP|RGB expected").addConstraintViolation();
        return false;
    }
}