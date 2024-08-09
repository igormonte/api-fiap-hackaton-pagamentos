package br.com.fiap.hackaton.autenticacao.application.dto.response;

import java.util.UUID;

public record UsuarioDto (
        UUID id,
        String cpf,
        String nome,
        String email) {
}
