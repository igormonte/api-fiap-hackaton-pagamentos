package br.com.fiap.hackaton.gateway.infrastructure.security.user;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
        private UUID id;
        private String nome;
        private String email;
}
