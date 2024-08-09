package br.com.fiap.hackaton.autenticacao.infrastructure.security.filter;

import br.com.fiap.hackaton.autenticacao.infrastructure.mapstruct.mapper.UsuarioMapper;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.entity.Usuario;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.service.TokenService;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.userdetails.InMemoryUsers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final InMemoryUsers inMemoryUsers;
    private final UsuarioMapper usuarioMapper;

    public TokenFilter(
            TokenService tokenService,
            InMemoryUsers inMemoryUsers, UsuarioMapper usuarioMapper) {
        this.tokenService = tokenService;
        this.inMemoryUsers = inMemoryUsers;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if(token != null){
            String login = tokenService.validateToken(token);
            Optional<Usuario> usuario = this.inMemoryUsers.findByUsername(login);

            if(usuario.isEmpty()) {
                throw new RuntimeException("Usuário não encontrado");
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(usuario.get(), null, usuario.get().getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
