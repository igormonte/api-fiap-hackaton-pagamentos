package br.com.fiap.hackaton.autenticacao.infrastructure.security.userdetails;

import br.com.fiap.hackaton.autenticacao.infrastructure.security.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@ConfigurationProperties(prefix = "autenticacao")
public class InMemoryUsers {
    private List<Usuario> usuario;

    public List<Usuario> getUsuario() {
        return usuario;
    }

    public void setUsuario(List<Usuario> usuario) {
        this.usuario = usuario;
    }

    public Optional<Usuario> findByUsername(String username) {

        return this.getUsuario().stream().filter( u ->
                u.getUsername().equals(username)
        ).findAny();

    }
}
