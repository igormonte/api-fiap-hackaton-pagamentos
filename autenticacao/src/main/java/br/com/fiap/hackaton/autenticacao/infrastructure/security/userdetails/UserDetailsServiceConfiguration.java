package br.com.fiap.hackaton.autenticacao.infrastructure.security.userdetails;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserDetailsServiceConfiguration {

    private final InMemoryUsers inMemoryUsers;

    public UserDetailsServiceConfiguration(InMemoryUsers inMemoryUsers) {

        this.inMemoryUsers = inMemoryUsers;
    }

    @Bean
    public UserDetailsService inMemoryUserDetails(PasswordEncoder passwordEncoder) {

        return new InMemoryUserDetailsManager(this.inMemoryUsers.getUsuario().stream().map(u-> {
                List<String> aut = u.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
                return User.builder()
                        .username(u.getUsername())
                        .password(passwordEncoder.encode(u.getPassword()))
                        .roles(u.getRole())
                        .build();
                }

        ).collect(Collectors.toList()));
    }

}
