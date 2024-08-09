package br.com.fiap.hackaton.autenticacao.domain.usecases;

import br.com.fiap.hackaton.autenticacao.domain.usecases.repository.AutenticacaoRepository;

public class AutenticarUseCaseImpl implements AutenticarUseCase {

    private final AutenticacaoRepository autenticacaoRepository;

    public AutenticarUseCaseImpl(AutenticacaoRepository autenticacaoRepository) {
        this.autenticacaoRepository = autenticacaoRepository;
    }

    @Override
    public String executar(String usuario, String senha) {
        return this.autenticacaoRepository.executar(usuario, senha);
    }

}
