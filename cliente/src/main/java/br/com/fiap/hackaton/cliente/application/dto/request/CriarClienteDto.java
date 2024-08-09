package br.com.fiap.hackaton.cliente.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CriarClienteDto(
        @NotBlank
        String cpf,
        @NotBlank
        String nome,
        @NotBlank
        String email,
        @NotBlank
        String telefone,
        @NotBlank
        String rua,
        @NotBlank
        String cidade,
        @NotBlank
        String estado,
        @NotBlank
        String cep,
        @NotBlank
        String pais) {
}
