package br.com.fiap.hackaton.cliente.domain.usecases;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.RegistrarClienteRepository;

public class RegistrarClienteUseCaseImpl implements RegistrarClienteUseCase {

    private final RegistrarClienteRepository registrarClienteRepository;

    public RegistrarClienteUseCaseImpl(RegistrarClienteRepository registrarClienteRepository) {
        this.registrarClienteRepository = registrarClienteRepository;
    }

    @Override
    public Cliente executar(Cliente cliente) {
        return this.registrarClienteRepository.executar(cliente);
    }

}
