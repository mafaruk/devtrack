package com.devtrack.backend_java.validator;

import com.devtrack.backend_java.validator.annotation.StrongPassword;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
           if (password == null) {
            return false;
        }
        boolean hasMinLength = password.length() >= 6;
        boolean hasUppercase = password.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        return hasMinLength && hasUppercase && hasDigit;
    }

}
