package br.com.fiap.hackaton.autenticacao.infrastructure.application.config;

import br.com.fiap.hackaton.autenticacao.application.gateway.AutenticacaoAuthGateway;
import br.com.fiap.hackaton.autenticacao.domain.usecases.repository.AutenticacaoRepository;
import br.com.fiap.hackaton.autenticacao.infrastructure.mapstruct.mapper.UsuarioMapper;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Component;

@Component
public class ApplicationConfig {

    @Bean
    AutenticacaoRepository getAutenticacaoRepository(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UsuarioMapper usuarioMapper
    ) {
        return new AutenticacaoAuthGateway(
                authenticationManager,
                tokenService,
                usuarioMapper
        );
    }

}
