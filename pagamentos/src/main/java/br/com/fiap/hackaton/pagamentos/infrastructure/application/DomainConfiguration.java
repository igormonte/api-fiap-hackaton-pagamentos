package br.com.fiap.hackaton.pagamentos.infrastructure.application;

import br.com.fiap.hackaton.pagamentos.application.gateway.BuscaCartaoDbGateway;
import br.com.fiap.hackaton.pagamentos.application.gateway.BuscaPagamentoDbGateway;
import br.com.fiap.hackaton.pagamentos.application.gateway.RegistraPagamentoDbGateway;
import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCaseImpl;
import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCaseImpl;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.CartaoMessagingGateway;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.CartaoMapper;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
@Component
public class DomainConfiguration {
    @Bean
    RegistrarPagamentoUseCase getRegistrarPagamentoUseCase(
            RegistraPagamentoRepository pagamentoRepository,
            BuscaCartaoRepository buscarCartaoRepository,
            BuscaPagamentoRepository buscaPagamentoRepository) {
        return new RegistrarPagamentoUseCaseImpl(
                pagamentoRepository,
                buscarCartaoRepository,
                buscaPagamentoRepository);
    }
    @Bean
    BuscaPagamentoUseCase getBuscaPagamentoUseCase(
            BuscaPagamentoRepository buscaPagamentoRepository
    ) {
        return new BuscaPagamentoUseCaseImpl(buscaPagamentoRepository);
    }

    @Bean
    RegistraPagamentoRepository getPagamentoRepository(
            PagamentoDbEntityRepository pagamentoDbEntityRepository,
            PagamentoMapper pagamentoMapper) {
        return new RegistraPagamentoDbGateway(
                pagamentoDbEntityRepository,
                pagamentoMapper);
    }

    @Bean
    BuscaPagamentoRepository getBuscaPagamentoRepository(
            PagamentoDbEntityRepository pagamentoDbEntityRepository,
            PagamentoMapper pagamentoMapper) {
        return new BuscaPagamentoDbGateway(
                pagamentoDbEntityRepository,
                pagamentoMapper);
    }

    @Bean
    BuscaCartaoRepository getBuscaCartaoRepository(
            CartaoMessagingGateway cartaoMessagingGateway,
            CartaoMapper cartaoMapper) {
        return new BuscaCartaoDbGateway(
                cartaoMessagingGateway,
                cartaoMapper);
    }

}
