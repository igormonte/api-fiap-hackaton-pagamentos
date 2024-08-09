package br.com.fiap.hackaton.cliente.infrastructure.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity(name = "cliente")
public class ClienteDbEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
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
