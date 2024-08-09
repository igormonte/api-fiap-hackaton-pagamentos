package br.com.fiap.hackaton.cliente.domain.usecases.repository;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;

public interface RegistrarClienteRepository {
    Cliente executar(Cliente usuario);
}
