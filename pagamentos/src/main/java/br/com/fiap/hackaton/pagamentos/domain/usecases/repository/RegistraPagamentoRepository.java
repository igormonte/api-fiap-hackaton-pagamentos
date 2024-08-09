package br.com.fiap.hackaton.pagamentos.domain.usecases.repository;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;

public interface RegistraPagamentoRepository {
    Pagamento registrarPagamento(Pagamento pagamento);
}
