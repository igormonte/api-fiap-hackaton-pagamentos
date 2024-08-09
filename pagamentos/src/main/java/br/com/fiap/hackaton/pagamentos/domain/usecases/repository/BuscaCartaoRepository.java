package br.com.fiap.hackaton.pagamentos.domain.usecases.repository;

import br.com.fiap.hackaton.pagamentos.domain.cartao.entity.Cartao;

public interface BuscaCartaoRepository {

    public Cartao porCpfENumero(String cpf, String numero);
}
