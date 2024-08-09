package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;

import java.util.List;
import java.util.UUID;

public interface BuscaPagamentoUseCase {

    List<Pagamento> porIdCliente(UUID id);

    public List<Pagamento> porCpfENumero(String cpf, String numero);
}
