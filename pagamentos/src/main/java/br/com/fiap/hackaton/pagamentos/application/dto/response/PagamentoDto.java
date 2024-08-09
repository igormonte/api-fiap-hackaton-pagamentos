package br.com.fiap.hackaton.pagamentos.application.dto.response;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.MetodoPagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.StatusPagamento;

import java.math.BigDecimal;

public record PagamentoDto(
        BigDecimal valor,
        String descricao,
        MetodoPagamento metodo_pagamento,
        StatusPagamento status
) {
}
