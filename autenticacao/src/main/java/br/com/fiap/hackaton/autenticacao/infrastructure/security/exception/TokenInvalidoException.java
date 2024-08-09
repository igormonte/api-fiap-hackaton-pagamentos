package br.com.fiap.hackaton.autenticacao.infrastructure.security.exception;

public class TokenInvalidoException extends RuntimeException {

    public TokenInvalidoException() {
        super();
    }

    public TokenInvalidoException(String message) {
        super(message);
    }

}
