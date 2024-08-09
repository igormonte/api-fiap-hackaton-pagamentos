package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;

import java.util.List;
import java.util.UUID;

public class BuscaPagamentoDbGateway implements BuscaPagamentoRepository {
    private final PagamentoDbEntityRepository pagamentoDbEntityRepository;
    private final PagamentoMapper pagamentoMapper;

    public BuscaPagamentoDbGateway(
            PagamentoDbEntityRepository pagamentoDbEntityRepository,
            PagamentoMapper pagamentoMapper) {
        this.pagamentoDbEntityRepository = pagamentoDbEntityRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    @Override
    public List<Pagamento> porIdCliente(UUID id) {
        return this.pagamentoMapper.toPagamentoList(
                this.pagamentoDbEntityRepository.findByIdCliente(id));
    }

    @Override
    public List<Pagamento> porCpfAndNumero(String cpf, String numero) {
        return this.pagamentoMapper.toPagamentoList(
                this.pagamentoDbEntityRepository.findByCpfAndNumero(cpf,numero));
    }
}
