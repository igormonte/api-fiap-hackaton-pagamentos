package br.com.fiap.hackaton.autenticacao.application.handler;

import br.com.fiap.hackaton.autenticacao.infrastructure.security.exception.JwtMauGeradoException;
import br.com.fiap.hackaton.autenticacao.infrastructure.security.exception.TokenInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<HandlerDto> handleTokenInvalidoException(
            TokenInvalidoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(JwtMauGeradoException.class)
    public ResponseEntity<HandlerDto> handleclass(
            JwtMauGeradoException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<HandlerDto> handleUsernameNotFoundException(
            UsernameNotFoundException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HandlerDto> handleBadCredentialsException(
            BadCredentialsException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
    @ExceptionHandler( AuthenticationException.class)
    public ResponseEntity<HandlerDto> handleAuthenticationException(
            AuthenticationException e
    ) {
        HandlerDto errorResponse =
                new HandlerDto("Runtime error", List.of(e.getMessage()));
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorResponse);
    }
}
