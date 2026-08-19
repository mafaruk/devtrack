package com.devtrack.backend_java.validator;


import com.devtrack.backend_java.utils.Role;
import com.devtrack.backend_java.validator.annotation.ValidRole;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleValidator implements ConstraintValidator<ValidRole, Role>{

    @Override
    public boolean isValid(Role role, ConstraintValidatorContext context) {
      if(role.equals(Role.ADMIN)|| role.equals(Role.USER)){
        return true;
      }
      return false;
        
    }

}
