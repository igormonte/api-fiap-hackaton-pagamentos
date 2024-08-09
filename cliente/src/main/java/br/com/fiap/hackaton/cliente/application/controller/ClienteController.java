package br.com.fiap.hackaton.cliente.application.controller;

import br.com.fiap.hackaton.cliente.application.dto.request.CriarClienteDto;
import br.com.fiap.hackaton.cliente.application.dto.response.ClienteDto;
import br.com.fiap.hackaton.cliente.domain.usecases.BuscarClienteUseCase;
import br.com.fiap.hackaton.cliente.domain.usecases.RegistrarClienteUseCase;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cliente")
public class ClienteController{

    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final BuscarClienteUseCase buscarClienteUseCase;
    private final ClienteMapper clienteMapper;

    public ClienteController(
            RegistrarClienteUseCase registrarClienteUseCase,
            BuscarClienteUseCase buscarClienteUseCase, ClienteMapper clienteMapper) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.buscarClienteUseCase = buscarClienteUseCase;
        this.clienteMapper = clienteMapper;
    }

    @PostMapping("")
    public ResponseEntity<ClienteDto> criarConta(
            @Valid @RequestBody CriarClienteDto cliente) {
        return ResponseEntity.ok(
                this.clienteMapper.toClienteDto(
                        this.registrarClienteUseCase.executar(
                                this.clienteMapper.toCliente(cliente))));
    }

    @GetMapping("")
    public ResponseEntity<ClienteDto> buscarPorCpf(
            @RequestParam("cpf") String cpf) {
        return ResponseEntity.ok(
                this.clienteMapper.toClienteDto(
                        this.buscarClienteUseCase.porCpf(cpf)));
    }


}
