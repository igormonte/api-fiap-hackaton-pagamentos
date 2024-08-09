package br.com.fiap.hackaton.cartao.application.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardExpirationValidator.class)
public @interface CardExpiration {

    String message() default "O padrão de dado deve ser MM/yy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
