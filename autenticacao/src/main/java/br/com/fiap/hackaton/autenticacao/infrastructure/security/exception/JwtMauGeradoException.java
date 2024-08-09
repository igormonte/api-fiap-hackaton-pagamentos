package br.com.fiap.hackaton.autenticacao.infrastructure.security.exception;

public class JwtMauGeradoException extends RuntimeException {

    public JwtMauGeradoException() {
        super();
    }

    public JwtMauGeradoException(String message) {
        super(message);
    }

}
