package br.com.fiap.hackaton.pagamentos.domain.usecases.repository;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;

import java.util.List;
import java.util.UUID;

public interface BuscaPagamentoRepository {

    List<Pagamento> porCpfAndNumero(String cpf, String numero);

    List<Pagamento> porIdCliente(UUID id);
}
