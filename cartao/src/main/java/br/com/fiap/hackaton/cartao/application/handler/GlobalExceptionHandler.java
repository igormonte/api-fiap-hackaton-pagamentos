package br.com.fiap.hackaton.cartao.application.handler;

import br.com.fiap.hackaton.cartao.domain.cartao.exception.CartaoJaCadastradoException;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.CartaoNaoEncontradoException;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.NumeroMaximoCartoesAtingidoException;
import br.com.fiap.hackaton.cartao.domain.cliente.exception.ClienteNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HandlerDto> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        Collections.sort(errors);
        HandlerDto errorResponse =
                new HandlerDto("Validation error", errors);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(CartaoJaCadastradoException.class)
    public ResponseEntity<HandlerDto> handleCartaoJaCadastradoException(
            CartaoJaCadastradoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(CartaoNaoEncontradoException.class)
    public ResponseEntity<HandlerDto> handleCartaoNaoEncontradoException(
            CartaoNaoEncontradoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(NumeroMaximoCartoesAtingidoException.class)
    public ResponseEntity<HandlerDto> handleNumeroMaximoCartoesAtingidoException(
            NumeroMaximoCartoesAtingidoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<HandlerDto> handleClienteNaoEncontradoException(
            ClienteNaoEncontradoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
