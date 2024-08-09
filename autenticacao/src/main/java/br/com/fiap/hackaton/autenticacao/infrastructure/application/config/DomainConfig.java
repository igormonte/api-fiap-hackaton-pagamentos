package br.com.fiap.hackaton.autenticacao.infrastructure.application.config;

import br.com.fiap.hackaton.autenticacao.domain.usecases.AutenticarUseCase;
import br.com.fiap.hackaton.autenticacao.domain.usecases.AutenticarUseCaseImpl;
import br.com.fiap.hackaton.autenticacao.domain.usecases.repository.AutenticacaoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DomainConfig {

    @Bean
    AutenticarUseCase getAutenticarUseCase(
            AutenticacaoRepository loginUsuarioRepository
    ) {
        return new AutenticarUseCaseImpl(loginUsuarioRepository);
    }

}
