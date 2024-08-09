package br.com.fiap.hackaton.cliente.infrastructure.db.repository;

import br.com.fiap.hackaton.cliente.infrastructure.db.entity.ClienteDbEntity;
import br.com.fiap.hackaton.cliente.infrastructure.helper.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ClienteRepositoryTest {

    @Mock
    private ClienteRepository clienteRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarCliente() {

        var cliente = ClienteHelper.gerarClienteDbEntity();
        when(clienteRepository.save(any(ClienteDbEntity.class))).thenReturn(cliente);

        var clienteArmazenado = clienteRepository.save(cliente);

        verify(clienteRepository, times(1)).save(cliente);
        assertThat(clienteArmazenado)
                .isInstanceOf(ClienteDbEntity.class)
                .isNotNull()
                .isEqualTo(cliente);
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getId)
                .isEqualTo(cliente.getId());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getCep)
                .isEqualTo(cliente.getCep());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getCpf)
                .isEqualTo(cliente.getCpf());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getRua)
                .isEqualTo(cliente.getRua());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getCidade)
                .isEqualTo(cliente.getCidade());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getEstado)
                .isEqualTo(cliente.getEstado());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getEmail)
                .isEqualTo(cliente.getEmail());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getPais)
                .isEqualTo(cliente.getPais());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getTelefone)
                .isEqualTo(cliente.getTelefone());
        assertThat(clienteArmazenado)
                .extracting(ClienteDbEntity::getNome)
                .isEqualTo(cliente.getNome());
    }

    @Test
    void devePermitirConsultarSeClienteExiste() {
        var cpf = "12312312312";
        var cliente = ClienteHelper.gerarClienteDbEntity();
        cliente.setCpf(cpf);

        var retorno = true;

        when(clienteRepository.existsByCpf(any(String.class)))
                .thenReturn(retorno);

        var retornoArmazenado =
                clienteRepository.existsByCpf(cpf);

        verify(clienteRepository, times(1))
                .existsByCpf(cpf);
        assertThat(retornoArmazenado)
                .isTrue();
    }

    @Test
    void devePermitirConsultarClientePorCpf() {
        var cpf = "12312312312";
        var cliente = ClienteHelper.gerarClienteDbEntity();
        cliente.setCpf(cpf);

        var clienteOptional = Optional.of(cliente);

        when(clienteRepository.findByCpf(any(String.class)))
                .thenReturn(clienteOptional);

        var clienteOptionalArmazenado =
                clienteRepository.findByCpf(cpf);

        verify(clienteRepository, times(1))
                .findByCpf(cpf);
        assertThat(clienteOptionalArmazenado)
                .isNotNull()
                .isPresent();
        clienteOptionalArmazenado.ifPresent(clienteArmazenado -> {
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getId)
                    .isEqualTo(cliente.getId());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getCep)
                    .isEqualTo(cliente.getCep());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getCpf)
                    .isEqualTo(cliente.getCpf());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getRua)
                    .isEqualTo(cliente.getRua());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getCidade)
                    .isEqualTo(cliente.getCidade());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getEstado)
                    .isEqualTo(cliente.getEstado());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getEmail)
                    .isEqualTo(cliente.getEmail());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getPais)
                    .isEqualTo(cliente.getPais());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getTelefone)
                    .isEqualTo(cliente.getTelefone());
            assertThat(clienteArmazenado)
                    .extracting(ClienteDbEntity::getNome)
                    .isEqualTo(cliente.getNome());
        });
    }

}