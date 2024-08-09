package br.com.fiap.hackaton.cliente.application.controller;

import br.com.fiap.hackaton.cliente.application.dto.request.CriarClienteDto;
import br.com.fiap.hackaton.cliente.application.dto.response.ClienteDto;
import br.com.fiap.hackaton.cliente.application.handler.GlobalExceptionHandler;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.BuscarClienteUseCase;
import br.com.fiap.hackaton.cliente.domain.usecases.RegistrarClienteUseCase;
import br.com.fiap.hackaton.cliente.infrastructure.helper.ClienteHelper;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapperTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RestController;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ClienteControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RegistrarClienteUseCase registrarClienteUseCase;

    @Mock
    private BuscarClienteUseCase buscarClienteUseCase;

    @Mock
    private ClienteMapper clienteMapper;

    @InjectMocks
    private ClienteController clienteController;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clienteController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class CriarConta {

        @Test
        void deveCriarContaComSucesso() throws Exception {
            CriarClienteDto criarClienteDto = ClienteHelper.gerarCriarClienteDto();
            Cliente cliente = ClienteHelper.gerarCliente();
            ClienteDto clienteDto = new ClienteDto(
                    cliente.getId(),
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getEmail());

            when(clienteMapper.toCliente(criarClienteDto)).thenReturn(cliente);
            when(registrarClienteUseCase.executar(cliente)).thenReturn(cliente);
            when(clienteMapper.toClienteDto(cliente)).thenReturn(clienteDto);

            mockMvc.perform(post("/cliente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(criarClienteDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(clienteDto.id().toString()))
                    .andExpect(jsonPath("$.email").value(clienteDto.email()))
                    .andExpect(jsonPath("$.nome").value(clienteDto.nome()))
                    .andExpect(jsonPath("$.cpf").value(clienteDto.cpf()));

            verify(clienteMapper, times(1)).toCliente(criarClienteDto);
            verify(registrarClienteUseCase, times(1)).executar(cliente);
            verify(clienteMapper, times(1)).toClienteDto(cliente);
        }

        @Test
        void deveGerarExcecao_QuandoCriarConta_ComPayloadInvalido() throws Exception {
            String invalidPayload = "{ \"nome\": \"\", \"cpf\": \"\" }";

            mockMvc.perform(post("/cliente")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(invalidPayload))
                    .andExpect(status().isInternalServerError());

            verify(clienteMapper, never()).toCliente(any(CriarClienteDto.class));
            verify(registrarClienteUseCase, never()).executar(any(Cliente.class));
            verify(clienteMapper, never()).toClienteDto(any(Cliente.class));
        }
    }

    @Nested
    class BuscarPorCpf {

        @Test
        void deveBuscarPorCpfComSucesso() throws Exception {
            String cpf = "12345678900";
            Cliente cliente = ClienteHelper.gerarCliente();
            cliente.setCpf(cpf);
            ClienteDto clienteDto = new ClienteDto(
                    cliente.getId(),
                    cliente.getCpf(),
                    cliente.getNome(),
                    cliente.getEmail());

            when(buscarClienteUseCase.porCpf(cpf)).thenReturn(cliente);
            when(clienteMapper.toClienteDto(cliente)).thenReturn(clienteDto);

            mockMvc.perform(get("/cliente?cpf={cpf}", cpf)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(clienteDto.id().toString()))
                    .andExpect(jsonPath("$.email").value(clienteDto.email()))
                    .andExpect(jsonPath("$.nome").value(clienteDto.nome()))
                    .andExpect(jsonPath("$.cpf").value(clienteDto.cpf()));

            verify(buscarClienteUseCase, times(1)).porCpf(cpf);
            verify(clienteMapper, times(1)).toClienteDto(cliente);
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
