package br.com.fiap.hackaton.cartao.domain.cliente.entity;

import lombok.Data;

import java.util.UUID;

@Data
public class Cliente {

        private UUID id;
        private String cpf;
        private String nome;
        private String email;
        private String telefone;
        private String rua;
        private String cidade;
        private String estado;
        private String cep;
        private String pais;

}
