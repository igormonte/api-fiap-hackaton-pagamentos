package br.com.fiap.hackaton.cartao.domain.cartao.exception;

public class CartaoJaCadastradoException extends RuntimeException {

    public CartaoJaCadastradoException() {
        super();
    }

    public CartaoJaCadastradoException(String message) {
        super(message);
    }

}
