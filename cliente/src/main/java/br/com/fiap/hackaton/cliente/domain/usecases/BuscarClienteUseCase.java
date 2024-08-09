package br.com.fiap.hackaton.cliente.domain.usecases;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;

import java.util.UUID;

public interface BuscarClienteUseCase {

    Cliente porCpf(String cpf);
    Cliente porId(UUID id);

}
