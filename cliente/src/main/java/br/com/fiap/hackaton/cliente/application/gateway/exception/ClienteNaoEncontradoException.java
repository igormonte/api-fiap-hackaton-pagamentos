package br.com.fiap.hackaton.cliente.application.gateway.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException() {
        super();
    }

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

}
