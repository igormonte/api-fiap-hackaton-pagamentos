package br.com.fiap.hackaton.autenticacao.domain.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Usuario {
        private UUID id;
        private String cpf;
        private String nome;
        private String email;
        private String senha;

}
