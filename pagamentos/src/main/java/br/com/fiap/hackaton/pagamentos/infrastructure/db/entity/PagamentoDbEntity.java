package br.com.fiap.hackaton.pagamentos.infrastructure.db.entity;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.MetodoPagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.StatusPagamento;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity(name = "pagamento")
public class PagamentoDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID idCartao;
    private UUID idCliente;
    private String cpf;
    private String numero;
    private LocalDate dataValidade;
    private String cvv;
    private String descricao;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento status;
    private BigDecimal valor;

}
