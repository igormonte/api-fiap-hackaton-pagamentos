package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.CartaoVencidoException;
import br.com.fiap.hackaton.pagamentos.domain.cartao.exception.SemLimiteException;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCase;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
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
public class RegistraPagamentoDbGatewayIT {


    @Autowired
    private RegistraPagamentoRepository pagamentoRepository;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    @Autowired
    private PagamentoDbEntityRepository pagamentoDbEntityRepository;

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
            var pagamento = PagamentoHelper.gerarPagamentoDbEntity();

            var pagamentoArmazenado = pagamentoDbEntityRepository.save(pagamento);

            assertThat(pagamentoArmazenado)
                    .isInstanceOf(PagamentoDbEntity.class)
                    .isNotNull()
                    .isEqualTo(pagamento);
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getId)
                    .isEqualTo(pagamento.getId());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getIdCartao)
                    .isEqualTo(pagamento.getIdCartao());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getIdCliente)
                    .isEqualTo(pagamento.getIdCliente());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getCpf)
                    .isEqualTo(pagamento.getCpf());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getNumero)
                    .isEqualTo(pagamento.getNumero());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getDataValidade)
                    .isEqualTo(pagamento.getDataValidade());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getCvv)
                    .isEqualTo(pagamento.getCvv());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getDescricao)
                    .isEqualTo(pagamento.getDescricao());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getMetodoPagamento)
                    .isEqualTo(pagamento.getMetodoPagamento());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getStatus)
                    .isEqualTo(pagamento.getStatus());
            assertThat(pagamentoArmazenado)
                    .extracting(PagamentoDbEntity::getValor)
                    .isEqualTo(pagamento.getValor());
            verify(pagamentoDbEntityRepository, times(1)).save(pagamento);
        }
    }
}