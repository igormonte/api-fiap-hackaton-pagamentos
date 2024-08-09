package br.com.fiap.hackaton.cliente.application.gateway;

import br.com.fiap.hackaton.cliente.application.gateway.exception.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.BuscarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.db.repository.ClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;

import java.util.UUID;

public class BuscarClienteDbGateway implements BuscarClienteRepository {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public BuscarClienteDbGateway(
            ClienteRepository clienteRepository,
            ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente porCpf(String cpf) {
        return this.clienteMapper.toCliente(
                this.clienteRepository.findByCpf(cpf)
                        .orElseThrow(ClienteNaoEncontradoException::new));
    }

    @Override
    public Cliente porId(UUID id) {
        return this.clienteMapper.toCliente(
                this.clienteRepository.findById(id)
                        .orElseThrow(ClienteNaoEncontradoException::new));
    }
}
