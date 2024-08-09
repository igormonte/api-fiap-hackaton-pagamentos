package br.com.fiap.hackaton.cartao.domain.usecases.repository;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;

public interface GerarCartaoRepository {

    public Cartao executar(Cartao cartao);

}
