package br.com.fiap.hackaton.cliente.domain.usecases.repository;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;

import java.util.UUID;

public interface BuscarClienteRepository {

    Cliente porCpf(String cpf);
    Cliente porId(UUID id);

}
