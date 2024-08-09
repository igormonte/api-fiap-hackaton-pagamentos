package br.com.fiap.hackaton.autenticacao.application.controller;

import br.com.fiap.hackaton.autenticacao.application.dto.request.AutenticarDto;
import br.com.fiap.hackaton.autenticacao.application.dto.response.AccessDto;
import br.com.fiap.hackaton.autenticacao.application.dto.response.CheckDto;
import br.com.fiap.hackaton.autenticacao.domain.usecases.AutenticarUseCase;
import br.com.fiap.hackaton.autenticacao.infrastructure.mapstruct.mapper.UsuarioMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/autenticacao")
public class AutenticacaoController {
    private final AutenticarUseCase autenticarUseCase;
    private final UsuarioMapper usuarioMapper;

    public AutenticacaoController(
            AutenticarUseCase autenticarUseCase,
            UsuarioMapper usuarioMapper) {
        this.autenticarUseCase = autenticarUseCase;
        this.usuarioMapper = usuarioMapper;
    }

    @GetMapping("/check")
    public ResponseEntity<?> checkMe() {
        return ResponseEntity.ok(new CheckDto("Authorized", true));
    }

    @PostMapping("")
    public ResponseEntity<?> autenticacao(@RequestBody AutenticarDto autenticarDto) {
        return ResponseEntity.ok(
                new AccessDto(
                        this.autenticarUseCase.executar(autenticarDto.usuario(), autenticarDto.senha())));
    }


}
