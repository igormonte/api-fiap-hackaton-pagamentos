package br.com.fiap.hackaton.autenticacao.domain.usecases;

public interface AutenticarUseCase {
    String executar(String usuario, String senha);
}
