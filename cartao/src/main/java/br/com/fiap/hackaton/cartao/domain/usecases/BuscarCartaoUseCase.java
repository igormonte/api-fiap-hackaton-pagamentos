package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;

import java.util.List;
import java.util.Optional;

public interface BuscarCartaoUseCase {

    Optional<Cartao> porCpfENumeroCartao(String cpf, String numero);
    
    Optional<Cartao> porNumeroCartao(String numero);

    List<Cartao> porCpf(String cpf);

}
