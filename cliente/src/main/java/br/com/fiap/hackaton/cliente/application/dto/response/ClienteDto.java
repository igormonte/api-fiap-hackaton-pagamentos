package br.com.fiap.hackaton.cliente.application.dto.response;

import java.util.UUID;

public record ClienteDto(
        UUID id,
        String cpf,
        String nome,
        String email) {
}
