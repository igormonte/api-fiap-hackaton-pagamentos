package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.domain.usecases.RegistrarPagamentoUseCaseImpl;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.RegistraPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class RegistraPagamentoDbGatewayTest {

    @Mock
    private RegistraPagamentoRepository pagamentoRepository;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    @Mock
    private BuscaPagamentoRepository buscaPagamentoRepository;

    @Mock
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

    @Test
    void devePermitirRegistrarPagamento() {
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setValor(BigDecimal.valueOf(100));
        when(pagamentoDbEntityRepository.save(any(PagamentoDbEntity.class))).thenReturn(pagamento);

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