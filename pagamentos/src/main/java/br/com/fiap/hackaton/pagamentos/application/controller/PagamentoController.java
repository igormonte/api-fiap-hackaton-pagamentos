package br.com.fiap.hackaton.pagamentos.application.controller;

import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.AutorizacaoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.PagamentoDto;
import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.AutorizacaoMapper;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    private final RegistrarPagamentoUseCase registrarPagamentoUseCase;
    private final BuscaPagamentoUseCase buscaPagamentoUseCase;
    private final PagamentoMapper pagamentoMapper;
    private final AutorizacaoMapper autorizacaoMapper;

    public PagamentoController(
            RegistrarPagamentoUseCase registrarPagamentoUseCase,
            BuscaPagamentoUseCase buscaPagamentoUseCase,
            PagamentoMapper pagamentoMapper,
            AutorizacaoMapper autorizacaoMapper) {
        this.registrarPagamentoUseCase = registrarPagamentoUseCase;
        this.buscaPagamentoUseCase = buscaPagamentoUseCase;
        this.pagamentoMapper = pagamentoMapper;
        this.autorizacaoMapper = autorizacaoMapper;
    }

    @GetMapping("/cliente/{id}")
    public ResponseEntity<List<PagamentoDto>> consultaPagamentosPorIdCliente(@PathVariable String id) {
        return ResponseEntity.ok(
                this.pagamentoMapper.toPagamentoDtoList(
                        this.buscaPagamentoUseCase.porIdCliente(UUID.fromString(id))));
    }


    @PostMapping("")
    public ResponseEntity<AutorizacaoDto> registrarPagamento(
            @Valid @RequestBody RegistrarPagamentoDto registrarPagamentoDto) {
        return ResponseEntity.ok(
                this.autorizacaoMapper.toAutorizacaoDto(
                        this.registrarPagamentoUseCase.executar(
                                this.pagamentoMapper.toPagamento(registrarPagamentoDto))));

    }
}
