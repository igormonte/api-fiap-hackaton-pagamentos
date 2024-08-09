package br.com.fiap.hackaton.cliente.application.gateway.exception;

public class CpfJaCadastradoException extends RuntimeException {

    public CpfJaCadastradoException() {
        super();
    }

    public CpfJaCadastradoException(String message) {
        super(message);
    }

}
