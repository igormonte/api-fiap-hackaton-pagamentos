package br.com.fiap.hackaton.cartao.infrastructure.cliente.dto;

import java.util.UUID;

public record ClienteDto(
        UUID id,
        String cpf,
        String nome,
        String email) {
}
