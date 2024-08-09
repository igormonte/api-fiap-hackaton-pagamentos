package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.BuscaPagamentoUseCaseImpl;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class BuscaPagamentoDbGatewayTest {

    @Mock
    private BuscaPagamentoRepository buscaPagamentoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class BuscarPorIdCliente {

        @Test
        void devePermitirBuscarPagamentosPorIdCliente() {
            var idCliente = UUID.randomUUID();
            var pagamento = PagamentoHelper.gerarPagamento();
            pagamento.getCartao().setIdCliente(idCliente);

            var pagamentoList = List.of(pagamento);

            when(buscaPagamentoRepository.porIdCliente(any(UUID.class)))
                    .thenReturn(pagamentoList);

            var resultado = buscaPagamentoRepository.porIdCliente(idCliente);

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(pagamentoSalvo -> {
                        assertThat(pagamentoSalvo).isInstanceOf(Pagamento.class);
                        assertThat(pagamentoSalvo.getId())
                                .isEqualTo(pagamento.getId());
                        assertThat(pagamentoSalvo.getCartao().getId())
                                .isEqualTo(pagamento.getCartao().getId());
                        assertThat(pagamentoSalvo.getCartao().getIdCliente())
                                .isEqualTo(pagamento.getCartao().getIdCliente());
                        assertThat(pagamentoSalvo.getCartao().getCpf())
                                .isEqualTo(pagamento.getCartao().getCpf());
                        assertThat(pagamentoSalvo.getCartao().getNumero())
                                .isEqualTo(pagamento.getCartao().getNumero());
                        assertThat(pagamentoSalvo.getCartao().getDataValidade())
                                .isEqualTo(pagamento.getCartao().getDataValidade());
                        assertThat(pagamentoSalvo.getCartao().getCvv())
                                .isEqualTo(pagamento.getCartao().getCvv());
                        assertThat(pagamentoSalvo.getDescricao())
                                .isEqualTo(pagamento.getDescricao());
                        assertThat(pagamentoSalvo.getMetodoPagamento())
                                .isEqualTo(pagamento.getMetodoPagamento());
                        assertThat(pagamentoSalvo.getStatus())
                                .isEqualTo(pagamento.getStatus());
                        assertThat(pagamentoSalvo.getValor())
                                .isEqualTo(pagamento.getValor());
                    });
            verify(buscaPagamentoRepository, times(1)).porIdCliente(idCliente);
        }

        @Test
        void deveRetornarListaVazia_QuandoNenhumPagamentoEncontradoPorIdCliente() {
            var id = UUID.randomUUID();

            when(buscaPagamentoRepository.porIdCliente(any(UUID.class)))
                    .thenReturn(Collections.emptyList());

            var resultado = buscaPagamentoRepository.porIdCliente(id);

            assertThat(resultado).isEmpty();
            verify(buscaPagamentoRepository, times(1)).porIdCliente(id);
        }
    }

    @Nested
    class BuscarPorCpfENumero {

        @Test
        void devePermitirBuscarPagamentosPorCpfENumero() {
            var cpf = "12345678901";
            var numero = "1234567890123456";
            var pagamento = PagamentoHelper.gerarPagamento();
            pagamento.getCartao().setCpf(cpf);
            pagamento.getCartao().setNumero(numero);

            var pagamentoList = List.of(pagamento);

            when(buscaPagamentoRepository.porCpfAndNumero(anyString(), anyString()))
                    .thenReturn(pagamentoList);

            var resultado = buscaPagamentoRepository.porCpfAndNumero(cpf, numero);

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(1)
                    .allSatisfy(pagamentoSalvo -> {
                        assertThat(pagamentoSalvo).isInstanceOf(Pagamento.class);
                        assertThat(pagamentoSalvo.getId())
                                .isEqualTo(pagamento.getId());
                        assertThat(pagamentoSalvo.getCartao().getId())
                                .isEqualTo(pagamento.getCartao().getId());
                        assertThat(pagamentoSalvo.getCartao().getIdCliente())
                                .isEqualTo(pagamento.getCartao().getIdCliente());
                        assertThat(pagamentoSalvo.getCartao().getCpf())
                                .isEqualTo(pagamento.getCartao().getCpf());
                        assertThat(pagamentoSalvo.getCartao().getNumero())
                                .isEqualTo(pagamento.getCartao().getNumero());
                        assertThat(pagamentoSalvo.getCartao().getDataValidade())
                                .isEqualTo(pagamento.getCartao().getDataValidade());
                        assertThat(pagamentoSalvo.getCartao().getCvv())
                                .isEqualTo(pagamento.getCartao().getCvv());
                        assertThat(pagamentoSalvo.getDescricao())
                                .isEqualTo(pagamento.getDescricao());
                        assertThat(pagamentoSalvo.getMetodoPagamento())
                                .isEqualTo(pagamento.getMetodoPagamento());
                        assertThat(pagamentoSalvo.getStatus())
                                .isEqualTo(pagamento.getStatus());
                        assertThat(pagamentoSalvo.getValor())
                                .isEqualTo(pagamento.getValor());
                    });
            verify(buscaPagamentoRepository, times(1)).porCpfAndNumero(cpf, numero);
        }

        @Test
        void deveRetornarListaVazia_QuandoNenhumPagamentoEncontradoPorCpfENumero() {
            var cpf = "12345678901";
            var numero = "1234567890123456";

            when(buscaPagamentoRepository.porCpfAndNumero(anyString(), anyString()))
                    .thenReturn(Collections.emptyList());

            var resultado = buscaPagamentoRepository.porCpfAndNumero(cpf, numero);

            assertThat(resultado).isEmpty();
            verify(buscaPagamentoRepository, times(1)).porCpfAndNumero(cpf, numero);
        }
    }
}

