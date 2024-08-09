package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;

public class RegistraPagamentoDbGateway implements RegistraPagamentoRepository {
    private final PagamentoDbEntityRepository pagamentoDbEntityRepository;
    private final PagamentoMapper pagamentoMapper;

    public RegistraPagamentoDbGateway(
            PagamentoDbEntityRepository pagamentoDbEntityRepository,
            PagamentoMapper pagamentoMapper) {
        this.pagamentoDbEntityRepository = pagamentoDbEntityRepository;
        this.pagamentoMapper = pagamentoMapper;
    }

    @Override
    public Pagamento registrarPagamento(Pagamento pagamento) {
        return this.pagamentoMapper.toPagamento(
                this.pagamentoDbEntityRepository.save(
                        this.pagamentoMapper.toPagamentoDbEntity(pagamento)));
    }
}
