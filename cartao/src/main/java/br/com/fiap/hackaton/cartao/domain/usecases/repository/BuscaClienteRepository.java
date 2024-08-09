package br.com.fiap.hackaton.cartao.domain.usecases.repository;

import br.com.fiap.hackaton.cartao.domain.cliente.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface BuscaClienteRepository {

    public Optional<Cliente> buscaPorCpf(String cpf);

    public Optional<Cliente> buscaPorId(UUID id);

}
