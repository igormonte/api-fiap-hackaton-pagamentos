package br.com.fiap.hackaton.pagamentos.domain.cartao.exception;

public class SemLimiteException extends RuntimeException {

    public SemLimiteException() {
        super();
    }

    public SemLimiteException(String mensagem) {
        super(mensagem);

    }

}
