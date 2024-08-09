package br.com.fiap.hackaton.pagamentos.domain.pagamento.entity;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class Pagamento {

    private UUID id;
    private Cartao cartao;
    private String descricao;
    private MetodoPagamento metodoPagamento;
    private StatusPagamento status;
    private BigDecimal valor;

}
