package br.com.fiap.hackaton.pagamentos.application.gateway.exception;

public class CartaoNaoEncontradoException extends RuntimeException {

    public CartaoNaoEncontradoException() {
        super();
    }

    public CartaoNaoEncontradoException(String mensagem) {
        super(mensagem);

    }

}
