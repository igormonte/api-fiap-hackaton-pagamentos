package br.com.fiap.hackaton.cliente.infrastructure.application.config;

import br.com.fiap.hackaton.cliente.application.gateway.BuscarClienteDbGateway;
import br.com.fiap.hackaton.cliente.application.gateway.RegistrarClienteDbGateway;
import br.com.fiap.hackaton.cliente.domain.usecases.BuscarClienteUseCase;
import br.com.fiap.hackaton.cliente.domain.usecases.BuscarClienteUseCaseImpl;
import br.com.fiap.hackaton.cliente.domain.usecases.RegistrarClienteUseCase;
import br.com.fiap.hackaton.cliente.domain.usecases.RegistrarClienteUseCaseImpl;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.BuscarClienteRepository;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.RegistrarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.db.repository.ClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DomainConfig {

    @Bean
    BuscarClienteRepository getBuscarClienteRepository(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper) {
        return new BuscarClienteDbGateway(
                clienteRepository,
                clienteMapper);
    }
    @Bean
    RegistrarClienteRepository getRegistrarClienteRepository(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper
    ) {
        return new RegistrarClienteDbGateway(
                clienteRepository,
                clienteMapper);
    }

    @Bean
    BuscarClienteUseCase getBuscarClienteUseCase(
            BuscarClienteRepository buscarClienteRepository
    ) {
        return new BuscarClienteUseCaseImpl(buscarClienteRepository);
    }

    @Bean
    RegistrarClienteUseCase geRegistrarClienteUseCase(
            RegistrarClienteRepository registrarClienteRepository
    ) {
        return new RegistrarClienteUseCaseImpl(
                registrarClienteRepository
        );
    }

}
