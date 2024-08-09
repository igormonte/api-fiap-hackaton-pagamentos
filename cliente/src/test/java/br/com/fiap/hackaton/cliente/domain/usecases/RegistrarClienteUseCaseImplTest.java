package br.com.fiap.hackaton.cliente.domain.usecases;

import br.com.fiap.hackaton.cliente.domain.entity.Cliente;
import br.com.fiap.hackaton.cliente.domain.usecases.repository.RegistrarClienteRepository;
import br.com.fiap.hackaton.cliente.infrastructure.helper.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegistrarClienteUseCaseImplTest {

    @Mock
    private RegistrarClienteRepository registrarClienteRepository;

    @Mock
    private RegistrarClienteUseCase registrarClienteUseCase;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        registrarClienteUseCase = new RegistrarClienteUseCaseImpl(
                registrarClienteRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Executar {

        @Test
        void deveRegistrarClienteComSucesso() {
            Cliente clienteEsperado = ClienteHelper.gerarCliente();
            when(registrarClienteRepository.executar(clienteEsperado)).thenReturn(clienteEsperado);

            Cliente clienteRetornado = registrarClienteUseCase.executar(clienteEsperado);

            assertNotNull(clienteRetornado);
            assertThat(clienteRetornado)
                    .isInstanceOf(Cliente.class)
                    .isNotNull()
                    .isEqualTo(clienteEsperado);
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
            verify(registrarClienteRepository, times(1)).executar(clienteEsperado);
        }

    }
}
