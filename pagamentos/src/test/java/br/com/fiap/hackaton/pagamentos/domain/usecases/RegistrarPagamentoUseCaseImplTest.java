package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.CartaoVencidoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.SemLimiteException;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.*;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.CartaoHelper;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class RegistrarPagamentoUseCaseImplTest {

    private RegistrarPagamentoUseCaseImpl registrarPagamentoUseCase;

    @Mock
    private RegistraPagamentoRepository pagamentoRepository;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    @Mock
    private BuscaPagamentoRepository buscaPagamentoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        registrarPagamentoUseCase = new RegistrarPagamentoUseCaseImpl(
                pagamentoRepository, buscarCartaoRepository, buscaPagamentoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class Executar {

        @Test
        void devePermitirRegistrarPagamento() {
            var pagamento = PagamentoHelper.gerarPagamento();
            var cartao = pagamento.getCartao();
            pagamento.setValor(BigDecimal.valueOf(100));

            when(buscarCartaoRepository.porCpfENumero(anyString(), anyString()))
                    .thenReturn(cartao);
            when(buscaPagamentoRepository.porCpfAndNumero(anyString(), anyString()))
                    .thenReturn(Collections.emptyList());
            when(pagamentoRepository.registrarPagamento(any(Pagamento.class)))
                    .thenAnswer(i -> i.getArgument(0));

            var pagamentoRegistrado = registrarPagamentoUseCase.executar(pagamento);

            assertThat(pagamentoRegistrado)
                    .isInstanceOf(Pagamento.class)
                    .isNotNull();
            assertThat(pagamentoRegistrado.getDescricao())
                    .isEqualTo(pagamento.getDescricao());
            assertThat(pagamentoRegistrado.getValor())
                    .isEqualTo(pagamento.getValor());
            assertThat(pagamentoRegistrado.getStatus())
                    .isEqualTo(pagamento.getStatus());
            assertThat(pagamentoRegistrado.getId())
                    .isEqualTo(pagamento.getId());
            assertThat(pagamentoRegistrado.getCartao().getId())
                    .isEqualTo(pagamento.getCartao().getId());
            assertThat(pagamentoRegistrado.getCartao().getIdCliente())
                    .isEqualTo(pagamento.getCartao().getIdCliente());
            assertThat(pagamentoRegistrado.getCartao().getCpf())
                    .isEqualTo(pagamento.getCartao().getCpf());
            assertThat(pagamentoRegistrado.getCartao().getNumero())
                    .isEqualTo(pagamento.getCartao().getNumero());
            assertThat(pagamentoRegistrado.getCartao().getCvv())
                    .isEqualTo(pagamento.getCartao().getCvv());
            assertThat(pagamentoRegistrado.getCartao().getDataValidade())
                    .isEqualTo(pagamento.getCartao().getDataValidade());
            assertThat(pagamentoRegistrado.getCartao().getLimite())
                    .isEqualTo(pagamento.getCartao().getLimite());
            verify(pagamentoRepository, times(1)).registrarPagamento(pagamento);
        }

        @Test
        void deveGerarExcecao_QuandoCartaoVencido() {
            var cartao = CartaoHelper.gerarCartao();
            cartao.setDataValidade(LocalDate.now().minusDays(1));
            var pagamento = PagamentoHelper.gerarPagamento();

            when(buscarCartaoRepository.porCpfENumero(anyString(), anyString()))
                    .thenReturn(cartao);

            assertThatThrownBy(() -> registrarPagamentoUseCase.executar(pagamento))
                    .isInstanceOf(CartaoVencidoException.class);

            verify(pagamentoRepository, never()).registrarPagamento(any(Pagamento.class));
        }

        @Test
        void deveGerarExcecao_QuandoSemLimiteDisponivel() {
            var cartao = CartaoHelper.gerarCartao();
            cartao.setDataValidade(LocalDate.now().plusDays(1));
            cartao.setLimite(BigDecimal.valueOf(1000));
            var pagamento = PagamentoHelper.gerarPagamento();
            pagamento.setValor(BigDecimal.valueOf(1100));

            when(buscarCartaoRepository.porCpfENumero(anyString(), anyString()))
                    .thenReturn(cartao);
            when(buscaPagamentoRepository.porCpfAndNumero(anyString(), anyString()))
                    .thenReturn(List.of(PagamentoHelper.gerarPagamento()));

            assertThatThrownBy(() -> registrarPagamentoUseCase.executar(pagamento))
                    .isInstanceOf(SemLimiteException.class);

            verify(pagamentoRepository, never()).registrarPagamento(any(Pagamento.class));
        }
    }
}