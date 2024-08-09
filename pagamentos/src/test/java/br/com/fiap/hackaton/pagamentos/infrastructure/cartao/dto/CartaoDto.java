package br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CartaoDto(
    UUID id,
    UUID idCliente,
    String cpf,
    BigDecimal limite,
    String numero,
    LocalDate dataValidade,
    String cvv
){
}
