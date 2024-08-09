package br.com.fiap.hackaton.cliente.application.gateway;

import br.com.fiap.hackaton.cliente.application.gateway.exception.CpfJaCadastradoException;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.RegistrarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.db.repository.ClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegistrarClienteDbGateway implements RegistrarClienteRepository {
    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public RegistrarClienteDbGateway(
            ClienteRepository clienteRepository,
             ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    @Override
    public Cliente executar(Cliente cliente) {
        if (this.clienteRepository.existsByCpf(cliente.getCpf())) {
            throw new CpfJaCadastradoException("O CPF já está cadastrado!");
        }

        log.info(cliente.toString());

        return this.clienteMapper.toCliente(
                this.clienteRepository.save(
                        this.clienteMapper.toClienteDbEntity(cliente)));
    }

}
