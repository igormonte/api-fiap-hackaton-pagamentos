package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;

import java.util.List;
import java.util.UUID;

public class BuscaPagamentoUseCaseImpl implements BuscaPagamentoUseCase {

    private final BuscaPagamentoRepository buscaPagamentoRepository;

    public BuscaPagamentoUseCaseImpl(BuscaPagamentoRepository buscaPagamentoRepository) {
        this.buscaPagamentoRepository = buscaPagamentoRepository;
    }

    @Override
    public List<Pagamento> porIdCliente(UUID id) {
        return this.buscaPagamentoRepository.porIdCliente(id);
    }

    @Override
    public List<Pagamento> porCpfENumero(String cpf, String numero) {
        return this.buscaPagamentoRepository.porCpfAndNumero(cpf, numero);
    }
}
