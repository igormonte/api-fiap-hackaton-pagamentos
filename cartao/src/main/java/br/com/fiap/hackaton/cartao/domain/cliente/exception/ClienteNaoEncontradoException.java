package br.com.fiap.hackaton.cartao.domain.cliente.exception;

public class ClienteNaoEncontradoException extends RuntimeException {

    public ClienteNaoEncontradoException() {
        super();
    }

    public ClienteNaoEncontradoException(String message) {
        super(message);
    }

}
