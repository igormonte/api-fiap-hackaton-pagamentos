package br.com.fiap.hackaton.cartao.domain.usecases.repository;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;

import java.util.List;
import java.util.Optional;

public interface BuscaCartaoRepository {

    List<Cartao> porCpf(String cpf);

    Optional<Cartao> porCpfENumeroCartao(String cpf, String numero);

    Optional<Cartao> porNumeroCartao(String numero);
}
