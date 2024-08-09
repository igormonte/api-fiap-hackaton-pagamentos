package br.com.fiap.hackaton.cartao.domain.cartao.exception;

public class CartaoNaoEncontradoException extends RuntimeException {

    public CartaoNaoEncontradoException() {
        super();
    }

    public CartaoNaoEncontradoException(String message) {
        super(message);
    }

}
