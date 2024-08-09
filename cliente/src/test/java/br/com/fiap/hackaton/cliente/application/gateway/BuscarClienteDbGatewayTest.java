package br.com.fiap.hackaton.cliente.application.gateway;

import br.com.fiap.hackaton.cliente.application.gateway.exception.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.cliente.application.gateway.exception.CpfJaCadastradoException;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.entity.ClienteTest;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.BuscarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;
import br.com.fiap.hackaton.cliente.infrastructure.db.repository.ClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.helper.ClienteHelper;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapperTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BuscarClienteDbGatewayTest {
    private BuscarClienteRepository buscarClienteRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteMapper clienteMapper;

    AutoCloseable openMocks;


    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarClienteRepository = new BuscarClienteDbGateway(
                clienteRepository, clienteMapper);
    }


    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarPorCpf {

        @Test
        void deveRetornarCliente_QuandoBuscarPorCpf() {
            String cpf = "12345678901";
            ClienteDbEntity clienteEsperado = ClienteHelper.gerarClienteDbEntity();
            clienteEsperado.setCpf(cpf);
            Cliente clienteMapeado = ClienteHelper.gerarCliente();
            clienteMapeado.setId(clienteEsperado.getId());
            clienteMapeado.setCpf(cpf);
            when(clienteRepository.findByCpf(any(String.class))).thenReturn(Optional.of(clienteEsperado));
            when(clienteMapper.toCliente(any(ClienteDbEntity.class))).thenReturn(clienteMapeado);

            Cliente clienteRetornado = buscarClienteRepository.porCpf(cpf);

            assertThat(clienteRetornado)
                    .isNotNull();
            assertThat(clienteRetornado)
                    .extracting(Cliente::getId)
                    .isEqualTo(clienteEsperado.getId());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCep)
                    .isEqualTo(clienteEsperado.getCep());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCpf)
                    .isEqualTo(clienteEsperado.getCpf());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getRua)
                    .isEqualTo(clienteEsperado.getRua());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCidade)
                    .isEqualTo(clienteEsperado.getCidade());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getEstado)
                    .isEqualTo(clienteEsperado.getEstado());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getEmail)
                    .isEqualTo(clienteEsperado.getEmail());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getPais)
                    .isEqualTo(clienteEsperado.getPais());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getTelefone)
                    .isEqualTo(clienteEsperado.getTelefone());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getNome)
                    .isEqualTo(clienteEsperado.getNome());

            verify(clienteRepository, times(1)).findByCpf(cpf);
        }

        @Test
        void deveRetornarNulo_QuandoClienteNaoEncontradoPorCpf() {
            String cpf = "12345678901";
            when(clienteRepository.findByCpf(cpf)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> buscarClienteRepository.porCpf(cpf))
                    .isInstanceOf(ClienteNaoEncontradoException.class);

            verify(clienteRepository, times(1)).findByCpf(cpf);
        }
    }

    @Nested
    class BuscarPorId {

        @Test
        void deveRetornarCliente_QuandoBuscarPorId() {
            UUID id = UUID.randomUUID();
            ClienteDbEntity clienteEsperado = ClienteHelper.gerarClienteDbEntity();
            clienteEsperado.setId(id);
            Cliente clienteMapeado = ClienteHelper.gerarCliente();
            clienteMapeado.setId(id);
            when(clienteRepository.findById(any(UUID.class))).thenReturn(Optional.of(clienteEsperado));
            when(clienteMapper.toCliente(any(ClienteDbEntity.class))).thenReturn(clienteMapeado);

            Cliente clienteRetornado = buscarClienteRepository.porId(id);

            assertThat(clienteRetornado)
                    .isNotNull();
            assertThat(clienteRetornado)
                    .extracting(Cliente::getId)
                    .isEqualTo(clienteEsperado.getId());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCep)
                    .isEqualTo(clienteEsperado.getCep());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCpf)
                    .isEqualTo(clienteEsperado.getCpf());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getRua)
                    .isEqualTo(clienteEsperado.getRua());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getCidade)
                    .isEqualTo(clienteEsperado.getCidade());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getEstado)
                    .isEqualTo(clienteEsperado.getEstado());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getEmail)
                    .isEqualTo(clienteEsperado.getEmail());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getPais)
                    .isEqualTo(clienteEsperado.getPais());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getTelefone)
                    .isEqualTo(clienteEsperado.getTelefone());
            assertThat(clienteRetornado)
                    .extracting(Cliente::getNome)
                    .isEqualTo(clienteEsperado.getNome());

            verify(clienteRepository, times(1)).findById(id);
        }

        @Test
        void deveRetornarExcessao_QuandoClienteNaoEncontradoPorId() {
            UUID id = UUID.randomUUID();
            when(clienteRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> buscarClienteRepository.porId(id))
                    .isInstanceOf(ClienteNaoEncontradoException.class);

            verify(clienteRepository, times(1)).findById(id);
        }
    }
}
