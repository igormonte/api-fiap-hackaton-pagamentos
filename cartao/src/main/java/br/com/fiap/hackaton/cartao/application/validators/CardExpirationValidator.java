package br.com.fiap.hackaton.cartao.application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CardExpirationValidator implements ConstraintValidator<CardExpiration, String> {

    private static final String PATTERN = "(0?[1-9]|1[012])\\/([0-9][0-9])$";

    private static final Pattern pattern = Pattern.compile(PATTERN);
    int minimumAge;

    @Override
    public void initialize(CardExpiration constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String expiration, ConstraintValidatorContext context) {

        Matcher matcher = pattern.matcher(expiration);

        if (!matcher.matches()) {
            return false;

        }

        String month = matcher.group(1);
        String endYear = matcher.group(2);

        if(Integer.parseInt(month)<1 || Integer.parseInt(month) > 12) {
            throw new RuntimeException("Informação Mês incorreta!");
        }

        String initYear = String.valueOf(LocalDate.now().getYear()).substring(0,2);

        LocalDate expirationDate = LocalDate.of(
                Integer.parseInt(initYear + endYear),Integer.parseInt(month), 1);

        expirationDate = expirationDate
                .plusMonths(1)
                .minusDays(1);

        if(expirationDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cartão expirado!");

        }


        return true;
    }
}