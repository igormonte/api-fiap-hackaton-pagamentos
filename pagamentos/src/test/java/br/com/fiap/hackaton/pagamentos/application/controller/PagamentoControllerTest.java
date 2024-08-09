package br.com.fiap.hackaton.pagamentos.application.controller;

import br.com.fiap.hackaton.pagamentos.application.dto.request.RegistrarPagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.AutorizacaoDto;
import br.com.fiap.hackaton.pagamentos.application.dto.response.PagamentoDto;
import br.com.fiap.hackaton.pagamentos.application.handler.GlobalExceptionHandler;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.AutorizacaoMapper;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

class PagamentoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarPagamentoUseCase registrarPagamentoUseCase;

    @Mock
    private BuscaPagamentoUseCase buscaPagamentoUseCase;

    @Mock
    private PagamentoMapper pagamentoMapper;

    @Mock
    private AutorizacaoMapper autorizacaoMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        PagamentoController mensagemController = new PagamentoController(
                registrarPagamentoUseCase,
                buscaPagamentoUseCase,
                pagamentoMapper,
                autorizacaoMapper
        );
        mockMvc = MockMvcBuilders.standaloneSetup(mensagemController)
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
    class RegistrarPagamento {

        @Test
        void devePermitirRegistrarPagamento() throws Exception {
            Pagamento pagamento = PagamentoHelper.gerarPagamento();
            RegistrarPagamentoDto registrarPagamentoDto = PagamentoHelper.gerarRegistrarPagamentoDto();
            AutorizacaoDto autorizacaoDto = new AutorizacaoDto(pagamento.getId().toString());

            when(pagamentoMapper.toPagamento(any(RegistrarPagamentoDto.class))).thenReturn(pagamento);
            when(registrarPagamentoUseCase.executar(any(Pagamento.class))).thenReturn(pagamento);
            when(autorizacaoMapper.toAutorizacaoDto(any(Pagamento.class))).thenReturn(autorizacaoDto);

            mockMvc.perform(post("/pagamentos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(registrarPagamentoDto)))
                    .andExpect(status().isOk());

            verify(pagamentoMapper, times(1)).toPagamento(any(RegistrarPagamentoDto.class));
            verify(registrarPagamentoUseCase, times(1)).executar(any(Pagamento.class));
            verify(autorizacaoMapper, times(1)).toAutorizacaoDto(any(Pagamento.class));
        }

        @Test
        void deveGerarExcecao_QuandoRegistrarPagamento_CamposInvalidos() throws Exception {
            RegistrarPagamentoDto registrarPagamentoDto = new RegistrarPagamentoDto(
                    "12312312312",
                    "1234123412341234",
                    "12/20",
                    "123",
                    BigDecimal.valueOf(1000)); // Adicione campos inválidos se necessário

            mockMvc.perform(post("/pagamentos")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(registrarPagamentoDto)))
                    .andExpect(status().isInternalServerError());
        }
    }

    @Nested
    class ConsultaPagamentosPorIdCliente {

        @Test
        void devePermitirConsultarPagamentosPorIdCliente() throws Exception {
            UUID idCliente = UUID.randomUUID();
            Pagamento pagamento = PagamentoHelper.gerarPagamento();
            pagamento.getCartao().setIdCliente(idCliente);
            PagamentoDto pagamentoDto = PagamentoHelper.gerarPagamentoDto();
            List<PagamentoDto> pagamentoDtoList = List.of(pagamentoDto);

            when(buscaPagamentoUseCase.porIdCliente(any(UUID.class))).thenReturn(Collections.singletonList(pagamento));
            when(pagamentoMapper.toPagamentoDtoList(anyList())).thenReturn(pagamentoDtoList);

            mockMvc.perform(get("/pagamentos/cliente/{id}", idCliente.toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

            verify(buscaPagamentoUseCase, times(1)).porIdCliente(any(UUID.class));
            verify(pagamentoMapper, times(1)).toPagamentoDtoList(anyList());
        }

        @Test
        void deveGerarExcecao_QuandoConsultarPagamentosPorIdClienteInvalido() throws Exception {
            String id = "invalid-uuid";

            mockMvc.perform(get("/pagamentos/cliente/{id}", id)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isInternalServerError());
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
