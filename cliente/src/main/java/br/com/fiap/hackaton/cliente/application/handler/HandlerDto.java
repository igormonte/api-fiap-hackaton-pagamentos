package br.com.fiap.hackaton.cliente.application.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HandlerDto {

    private String message;
    private List<String> errors;
}
