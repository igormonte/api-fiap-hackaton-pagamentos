package br.com.fiap.hackaton.cartao.application.gateway;

import br.com.fiap.hackaton.cartao.domain.cliente.entity.Cliente;
import br.com.fiap.hackaton.cartao.domain.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaClienteRepository;
import br.com.fiap.hackaton.cartao.infrastructure.cliente.ClienteMessagingGateway;

import br.com.fiap.hackaton.cartao.infrastructure.cliente.dto.ClienteDto;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.ClienteMapper;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

public class BuscaClienteDbGateway implements BuscaClienteRepository {

    private final ClienteMessagingGateway clienteMessagingGateway;
    private final ClienteMapper clienteMapper;

    public BuscaClienteDbGateway(
            ClienteMessagingGateway clienteMessagingGateway,
            ClienteMapper clienteMapper) {
        this.clienteMessagingGateway = clienteMessagingGateway;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Optional<Cliente> buscaPorCpf(String cpf) {
        ResponseEntity<ClienteDto> clienteDto =
                this.clienteMessagingGateway.buscarPorCpf(cpf);

        if(clienteDto.getStatusCode().isError()) {
            throw new ClienteNaoEncontradoException("Cliente não encontrado!");
        }

        return Optional.ofNullable(
                this.clienteMapper.toCliente(clienteDto.getBody()));
    }

    @Override
    public Optional<Cliente> buscaPorId(UUID id) {
        return Optional.empty();
    }
}
