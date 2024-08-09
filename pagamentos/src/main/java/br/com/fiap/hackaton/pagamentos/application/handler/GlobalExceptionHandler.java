package br.com.fiap.hackaton.pagamentos.application.handler;

import br.com.fiap.hackaton.pagamentos.application.gateway.exception.CartaoNaoEncontradoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.CartaoVencidoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.SemLimiteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
    @ExceptionHandler(CartaoVencidoException.class)
    public ResponseEntity<HandlerDto> handleCartaoVencidoException(
            CartaoVencidoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(SemLimiteException.class)
    public ResponseEntity<HandlerDto> handleSemLimiteException(
            SemLimiteException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.PAYMENT_REQUIRED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<HandlerDto> handleIllegalArgumentException(
            IllegalArgumentException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
