package br.com.fiap.hackaton.cartao.application.controller;

import br.com.fiap.hackaton.cartao.application.dto.request.CriarCartaoDto;
import br.com.fiap.hackaton.cartao.application.dto.response.CartaoDto;
import br.com.fiap.hackaton.cartao.domain.usecases.BuscarCartaoUseCase;
import br.com.fiap.hackaton.cartao.domain.usecases.GerarCartaoUseCase;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cartao")
public class CartaoController {

    private final GerarCartaoUseCase gerarCartaoUseCase;
    private final BuscarCartaoUseCase buscarCartaoUseCase;
    private final CartaoMapper cartaoMapper;

    public CartaoController(GerarCartaoUseCase gerarCartaoUseCase,
                            BuscarCartaoUseCase buscarCartaoUseCase,
                            CartaoMapper cartaoMapper) {
        this.gerarCartaoUseCase = gerarCartaoUseCase;
        this.buscarCartaoUseCase = buscarCartaoUseCase;
        this.cartaoMapper = cartaoMapper;
    }

    @RequestMapping(method = RequestMethod.GET, value = "", params = "cpf")
    public ResponseEntity<List<CartaoDto>> buscaCartoesPorCpf(
            @RequestParam("cpf") String cpf
    ) {

        return ResponseEntity.ok(
                this.cartaoMapper.toCartaoDtoList(
                        this.buscarCartaoUseCase.porCpf(cpf)));
    }
    @RequestMapping(method = RequestMethod.GET, value = "", params = {"cpf", "numero"})
    public ResponseEntity<CartaoDto> buscaCartoesPorCpf(
            @RequestParam("cpf") String cpf,
            @RequestParam("numero") String numero
    ) {

        log.info(cpf);
        log.info(numero);

        return ResponseEntity.ok(
                this.cartaoMapper.toCartaoDto(
                        this.buscarCartaoUseCase.porCpfENumeroCartao(cpf, numero).orElseThrow()));
    }

    @PostMapping("")
    public ResponseEntity<CartaoDto> gerarCartaoCustomizado(
            @Valid @RequestBody CriarCartaoDto criarCartaoDto) {

        return ResponseEntity.ok(
                this.cartaoMapper.toCartaoDto(
                        this.gerarCartaoUseCase.gerarPlanoCustomizado(
                                criarCartaoDto.cpf(),
                                criarCartaoDto.limite(),
                                criarCartaoDto.numero(),
                                criarCartaoDto.validade(),
                                criarCartaoDto.cvv())));

    }


}
