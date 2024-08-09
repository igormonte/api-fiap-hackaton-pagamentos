package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.CartaoVencidoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.SemLimiteException;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class RegistrarPagamentoUseCaseImplIT {

    @Autowired
    private RegistrarPagamentoUseCase registrarPagamentoUseCase;

    @Autowired
    private RegistraPagamentoRepository pagamentoRepository;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    @Autowired
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
    class Executar {

        @Test
        void devePermitirRegistrarPagamento_QuandoCartaoValidoELimiteDisponivel() {
            var pagamento = PagamentoHelper.gerarPagamento();

            var pagamentoRegistrado = registrarPagamentoUseCase.executar(pagamento);

            assertThat(pagamentoRegistrado)
                    .isNotNull()
                    .isInstanceOf(Pagamento.class);
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

            assertThatThrownBy(() -> registrarPagamentoUseCase.executar(pagamento))
                    .isInstanceOf(SemLimiteException.class);
            verify(pagamentoRepository, never()).registrarPagamento(any(Pagamento.class));
        }
    }
}