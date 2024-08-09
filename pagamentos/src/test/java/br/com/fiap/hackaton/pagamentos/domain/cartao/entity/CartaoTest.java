package br.com.fiap.hackaton.pagamentos.domain.cartao.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CartaoTest {
    private UUID id;
    private UUID idCliente;
    private String cpf;
    private BigDecimal limite;
    private String numero;
    private LocalDate dataValidade;
    private String cvv;
}
