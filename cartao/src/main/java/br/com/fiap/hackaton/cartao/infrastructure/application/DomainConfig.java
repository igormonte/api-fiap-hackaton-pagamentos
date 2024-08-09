package br.com.fiap.hackaton.cartao.infrastructure.application;

import br.com.fiap.hackaton.cartao.application.gateway.BuscaCartaoDbGateaway;
import br.com.fiap.hackaton.cartao.application.gateway.BuscaClienteDbGateway;
import br.com.fiap.hackaton.cartao.application.gateway.GeraCartaoDbGateaway;
import br.com.fiap.hackaton.cartao.domain.usecases.BuscarCartaoUseCase;
import br.com.fiap.hackaton.cartao.domain.usecases.BuscarCartaoUseCaseImpl;
import br.com.fiap.hackaton.cartao.domain.usecases.GerarCartaoUseCase;
import br.com.fiap.hackaton.cartao.domain.usecases.GerarCartaoUseCaseImpl;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaClienteRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.GerarCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.cliente.ClienteMessagingGateway;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepository;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.ClienteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DomainConfig {

    @Bean
    BuscaClienteRepository getBuscaClienteRepository(
            ClienteMessagingGateway clienteMessagingGateway,
            ClienteMapper clienteMapper) {
        return new BuscaClienteDbGateway(
                clienteMessagingGateway,
                clienteMapper);
    }

    @Bean
    BuscarCartaoUseCase getBuscarCartaoUseCase(
            BuscaCartaoRepository buscarCartaoRepository
    ) {
        return new BuscarCartaoUseCaseImpl(buscarCartaoRepository);
    }


    @Bean
    GerarCartaoRepository getGerarCartaoRepository(
            CartaoDbEntityRepository cartaoDbEntityRepository,
            CartaoMapper cartaoMapper) {
        return new GeraCartaoDbGateaway(
                cartaoDbEntityRepository,
                cartaoMapper);
    }


    @Bean
    GerarCartaoUseCase getGerarCartaoUseCase(
            GerarCartaoRepository gerarCartaoRepository,
            BuscaClienteRepository buscaClienteRepository,
            BuscaCartaoRepository buscarCartaoRepository
    ) {
        return new GerarCartaoUseCaseImpl(
                gerarCartaoRepository,
                buscaClienteRepository,
                buscarCartaoRepository);
    }

    @Bean
    BuscaCartaoRepository getBuscaCartaoRepository(
            CartaoDbEntityRepository cartaoDbEntityRepository,
            CartaoMapper cartaoMapper
    ) {
        return new BuscaCartaoDbGateaway(
                cartaoDbEntityRepository,
                cartaoMapper);
    }


}
