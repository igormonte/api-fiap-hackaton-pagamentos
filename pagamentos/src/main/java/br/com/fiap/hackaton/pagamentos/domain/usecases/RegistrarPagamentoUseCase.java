package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;

public interface RegistrarPagamentoUseCase {

    public Pagamento executar(Pagamento pagamento);

}
