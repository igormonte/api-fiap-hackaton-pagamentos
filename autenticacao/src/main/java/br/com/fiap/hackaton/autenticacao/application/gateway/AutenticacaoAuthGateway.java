package br.com.fiap.hackaton.autenticacao.application.gateway;

import br.com.fiap.hackaton.autenticacao.domain.usecases.repository.AutenticacaoRepository;
import br.com.fiap.hackaton.autenticacao.infrastructure.mapstruct.mapper.UsuarioMapper;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
public class AutenticacaoAuthGateway implements AutenticacaoRepository {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UsuarioMapper usuarioMapper;

    public AutenticacaoAuthGateway(
            AuthenticationManager authenticationManager,
            TokenService tokenService,
            UsuarioMapper usuarioMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public String executar(String email, String senha) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(email, senha);

        Authentication auth = this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        return this.tokenService.generateToken((UserDetails) auth.getPrincipal());

    }

}
