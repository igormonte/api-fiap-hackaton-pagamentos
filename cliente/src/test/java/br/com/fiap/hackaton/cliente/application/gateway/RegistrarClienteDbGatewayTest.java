package br.com.fiap.hackaton.cliente.application.gateway;

import br.com.fiap.hackaton.cliente.application.gateway.exception.CpfJaCadastradoException;
import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.entity.ClienteTest;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.RegistrarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;
import br.com.fiap.hackaton.cliente.infrastructure.db.repository.ClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.helper.ClienteHelper;
import br.com.fiap.hackaton.cliente.infrastructure.mapstruct.mapper.ClienteMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
public class RegistrarClienteDbGatewayTest {

    private RegistrarClienteRepository registrarClienteRepository;

    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ClienteMapper clienteMapper;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        registrarClienteRepository = new RegistrarClienteDbGateway(
                clienteRepository, clienteMapper);
    }


    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Executar {

        @Test
        void deveRegistrarClienteComSucesso() {
            String cpf = "12312312312";
            Cliente clienteEnviado = ClienteHelper.gerarCliente();
            clienteEnviado.setCpf(cpf);
            ClienteDbEntity clienteEsperado = ClienteHelper.gerarClienteDbEntity();
            clienteEsperado.setCpf(cpf);
            clienteEsperado.setId(clienteEnviado.getId());
            when(clienteRepository.existsByCpf(any(String.class))).thenReturn(false);
            when(clienteMapper.toClienteDbEntity(any(Cliente.class))).thenReturn(clienteEsperado);
            when(clienteMapper.toCliente(any(ClienteDbEntity.class))).thenReturn(clienteEnviado);
            when(clienteRepository.save(any(ClienteDbEntity.class))).thenReturn(clienteEsperado);

            Cliente clienteRetornado = registrarClienteRepository.executar(clienteEnviado);

            assertNotNull(clienteRetornado);
            assertThat(clienteRetornado)
                    .isInstanceOf(Cliente.class)
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
            verify(clienteRepository, times(1)).save(any(ClienteDbEntity.class));
        }

        @Test
        void deveGerarExcessao_QuandoCpfJaCadastrado() {
            String cpf = "12312312312";
            Cliente clienteEnviado = ClienteHelper.gerarCliente();
            clienteEnviado.setCpf(cpf);
            ClienteDbEntity clienteEsperado = ClienteHelper.gerarClienteDbEntity();
            clienteEsperado.setCpf(cpf);
            clienteEsperado.setId(clienteEnviado.getId());
            when(clienteRepository.existsByCpf(cpf)).thenReturn(true);
            when(clienteMapper.toClienteDbEntity(any(Cliente.class))).thenReturn(clienteEsperado);
            when(clienteMapper.toCliente(any(ClienteDbEntity.class))).thenReturn(clienteEnviado);
            when(clienteRepository.save(any(ClienteDbEntity.class))).thenReturn(clienteEsperado);

            assertThatThrownBy(() -> registrarClienteRepository.executar(clienteEnviado))
                    .isInstanceOf(CpfJaCadastradoException.class);
            verify(clienteRepository, never()).save(any(ClienteDbEntity.class));
        }

    }
}
