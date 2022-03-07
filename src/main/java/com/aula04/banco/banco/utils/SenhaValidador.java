package com.aula04.banco.banco.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SenhaValidador implements ConstraintValidator<Senha, String> {
    @Override
    public void initialize(Senha constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String senha, ConstraintValidatorContext context) {
        if (senha.length() < 3 || senha.length() > 15) {
            return false;
        }
        return true;
    }
}
