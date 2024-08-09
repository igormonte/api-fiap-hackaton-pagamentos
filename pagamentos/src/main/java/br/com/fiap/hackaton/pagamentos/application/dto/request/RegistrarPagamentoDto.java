package br.com.fiap.hackaton.pagamentos.application.dto.request;

import br.com.fiap.hackaton.pagamentos.application.validators.CardExpiration;

import java.math.BigDecimal;

public record RegistrarPagamentoDto(
        String cpf,
        String numero,
        @CardExpiration
        String data_validade,
        String cvv,
        BigDecimal valor
) {
}
