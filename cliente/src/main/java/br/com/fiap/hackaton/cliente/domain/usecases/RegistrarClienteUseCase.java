package br.com.fiap.hackaton.cliente.domain.usecases;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;

public interface RegistrarClienteUseCase {
    Cliente executar(Cliente usuario);
}
