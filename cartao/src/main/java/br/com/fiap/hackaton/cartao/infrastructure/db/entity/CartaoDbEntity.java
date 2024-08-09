package br.com.fiap.hackaton.cartao.infrastructure.db.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "cartao")
public class CartaoDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idCliente;
    private String cpf;
    private BigDecimal limite;
    private String numero;
    private LocalDate dataValidade;
    private String cvv;

}
