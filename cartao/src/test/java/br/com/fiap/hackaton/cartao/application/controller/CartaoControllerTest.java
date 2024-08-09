package br.com.fiap.hackaton.cartao.application.controller;

import br.com.fiap.hackaton.cartao.application.dto.request.CriarCartaoDto;
import br.com.fiap.hackaton.cartao.application.dto.response.CartaoDto;
import br.com.fiap.hackaton.cartao.application.handler.GlobalExceptionHandler;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.NumeroCartao;
import br.com.fiap.hackaton.cartao.domain.usecases.BuscarCartaoUseCase;
import br.com.fiap.hackaton.cartao.domain.usecases.GerarCartaoUseCase;
import br.com.fiap.hackaton.cartao.infrastructure.helper.CartaoHelper;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GerarCartaoUseCase gerarCartaoUseCase;

    @Mock
    private BuscarCartaoUseCase buscarCartaoUseCase;

    @Mock
    private CartaoMapper cartaoMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        CartaoController cartaoController = new CartaoController(gerarCartaoUseCase, buscarCartaoUseCase, cartaoMapper);
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }, "/*")
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarCartoesPorCpf {

        @Test
        void devePermitirBuscarCartoesPorCpf() throws Exception {
            var cpf = "12345678900";
            var cartao = CartaoHelper.gerarCartao();
            cartao.setCpf(cpf);
            var cartaoDto = new CartaoDto(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    cpf,
                    new BigDecimal(1000),
                    "1234567890123456",
                    LocalDate.now().plusYears(1),
                    "123"
            );

            var cartaoList = List.of(CartaoHelper.gerarCartao());
            when(buscarCartaoUseCase.porCpf(anyString())).thenReturn(cartaoList);
            when(cartaoMapper.toCartaoDtoList(anyList())).thenReturn(Collections.singletonList(cartaoDto));

            mockMvc.perform(get("/cartao")
                            .param("cpf", cpf)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0]").exists());
            verify(buscarCartaoUseCase, times(1)).porCpf(anyString());
        }

        @Test
        void deveGerarExcecao_QuandoCpfNaoEncontrado() throws Exception {
            var cpf = "12345678900";
            when(buscarCartaoUseCase.porCpf(anyString())).thenReturn(Collections.emptyList());

            mockMvc.perform(get("/cartao")
                            .param("cpf", cpf)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isEmpty());
            verify(buscarCartaoUseCase, times(1)).porCpf(anyString());
        }
    }

    @Nested
    class BuscarCartoesPorCpfENumero {

        @Test
        void devePermitirBuscarCartoesPorCpfENumero() throws Exception {
            var cpf = "12345678900";
            var numero = "1234567890123456";
            var cartao = CartaoHelper.gerarCartao();
            cartao.setCpf(cpf);
            cartao.setNumero(NumeroCartao.of(numero));
            var cartaoDto = new CartaoDto(
                    UUID.randomUUID(),
                    UUID.randomUUID(),
                    cpf,
                    new BigDecimal(1000),
                    numero,
                    LocalDate.now().plusYears(1),
                    "123"
            );
            when(buscarCartaoUseCase.porCpfENumeroCartao(anyString(), anyString())).thenReturn(Optional.of(cartao));
            when(cartaoMapper.toCartaoDto(any(Cartao.class))).thenReturn(cartaoDto);

            mockMvc.perform(get("/cartao")
                            .param("cpf", cpf)
                            .param("numero", numero)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists());
            verify(buscarCartaoUseCase, times(1)).porCpfENumeroCartao(anyString(), anyString());
        }
    }

    @Nested
    class GerarCartaoCustomizado {

        @Test
        void devePermitirGerarCartaoCustomizado() throws Exception {
            var cartaoEsperado = CartaoHelper.gerarCartao();
            var criarCartaoDto =
                    new CriarCartaoDto(
                            cartaoEsperado.getCpf(),
                            "1234567890123456",
                            "12/29",
                            cartaoEsperado.getCvv().getNumero(),
                            cartaoEsperado.getLimite());
            var cartaoDto = new CartaoDto(
                    cartaoEsperado.getId(),
                    cartaoEsperado.getIdCliente(),
                    cartaoEsperado.getCpf(),
                    cartaoEsperado.getLimite(),
                    cartaoEsperado.getCvv().getNumero(),
                    LocalDate.now().plusYears(1),
                    cartaoEsperado.getCvv().getNumero()
            );

            when(gerarCartaoUseCase.gerarPlanoCustomizado(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString()))
                    .thenReturn(cartaoEsperado);
            when(cartaoMapper.toCartaoDto(any(Cartao.class))).thenReturn(cartaoDto);

            mockMvc.perform(post("/cartao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(criarCartaoDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").exists());
            verify(gerarCartaoUseCase, times(1)).gerarPlanoCustomizado(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString());
        }

        @Test
        void deveGerarExcecao_QuandoDadosInvalidos() throws Exception {
            var criarCartaoDto = new CriarCartaoDto(
                    "", null, "", "", null);

            mockMvc.perform(post("/cartao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(criarCartaoDto)))
                    .andExpect(status().isInternalServerError());
            verify(gerarCartaoUseCase, never()).gerarPlanoCustomizado(anyString(), any(BigDecimal.class), anyString(), anyString(), anyString());
        }
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
