package br.com.fiap.hackaton.pagamentos.domain.pagamento.entity;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.CartaoTest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PagamentoTest {

    private UUID id;
    private CartaoTest cartao;
    private String descricao;
    private MetodoPagamentoTest metodoPagamento;
    private StatusPagamentoTest status;
    private BigDecimal valor;

}
