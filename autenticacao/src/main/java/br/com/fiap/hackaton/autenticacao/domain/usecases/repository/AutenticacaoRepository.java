package br.com.fiap.hackaton.autenticacao.domain.usecases.repository;

public interface AutenticacaoRepository {
    String executar(String usuario, String senha);
}
