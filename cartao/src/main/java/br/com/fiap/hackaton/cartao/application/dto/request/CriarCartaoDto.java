package br.com.fiap.hackaton.cartao.application.dto.request;

import br.com.fiap.hackaton.cartao.application.validators.CardExpiration;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

public record CriarCartaoDto(
        String cpf,
//        @CreditCardNumber
        String numero,
        @CardExpiration
        String validade,
        String cvv,
        BigDecimal limite
) {
}
