package br.com.fiap.hackaton.cliente.domain.usecases;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.BuscarClienteRepository;

import java.util.UUID;

public class BuscarClienteUseCaseImpl implements BuscarClienteUseCase {

    private final BuscarClienteRepository buscarClienteRepository;

    public BuscarClienteUseCaseImpl(BuscarClienteRepository buscarClienteRepository) {
        this.buscarClienteRepository = buscarClienteRepository;
    }

    @Override
    public Cliente porCpf(String cpf) {
        return this.buscarClienteRepository.porCpf(cpf);
    }

    @Override
    public Cliente porId(UUID id) {
        return this.buscarClienteRepository.porId(id);
    }
}
