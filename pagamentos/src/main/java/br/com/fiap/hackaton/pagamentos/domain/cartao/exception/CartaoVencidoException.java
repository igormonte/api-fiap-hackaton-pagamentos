package br.com.fiap.hackaton.pagamentos.domain.cartao.exception;

public class CartaoVencidoException extends RuntimeException {

    public CartaoVencidoException() {
        super();
    }

    public CartaoVencidoException(String mensagem) {
        super(mensagem);

    }

}
