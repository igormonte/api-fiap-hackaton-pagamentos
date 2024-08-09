package br.com.fiap.hackaton.cartao.application.dto.response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record CartaoDto (
    UUID id,
    UUID idCliente,
    String cpf,
    BigDecimal limite,
    String numero,
    LocalDate dataValidade,
    String cvv
){
}
