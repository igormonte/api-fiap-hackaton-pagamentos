package br.com.fiap.hackaton.pagamentos.infrastructure.db.repository;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.MetodoPagamento;
import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.StatusPagamento;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PagamentoDbEntityRepositoryTest {

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
        when(pagamentoDbEntityRepository.save(any(PagamentoDbEntity.class))).thenReturn(pagamento);

        var pagamentoArmazenado = pagamentoDbEntityRepository.save(pagamento);

        verify(pagamentoDbEntityRepository, times(1)).save(pagamento);
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
    }

    @Test
    void devePermitirConsultarPagamentoPorId() {
        var id = UUID.randomUUID();
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setId(id);

        when(pagamentoDbEntityRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(pagamento));

        var pagamentoOptional = pagamentoDbEntityRepository.findById(id);

        verify(pagamentoDbEntityRepository, times(1)).findById(id);
        assertThat(pagamentoOptional)
                .isPresent()
                .containsSame(pagamento);
        pagamentoOptional.ifPresent(pagamentoArmazenado -> {
            assertThat(pagamentoArmazenado.getId())
                    .isEqualTo(pagamento.getId());
            assertThat(pagamentoArmazenado.getIdCartao())
                    .isEqualTo(pagamento.getIdCartao());
            assertThat(pagamentoArmazenado.getIdCliente())
                    .isEqualTo(pagamento.getIdCliente());
            assertThat(pagamentoArmazenado.getCpf())
                    .isEqualTo(pagamento.getCpf());
            assertThat(pagamentoArmazenado.getNumero())
                    .isEqualTo(pagamento.getNumero());
            assertThat(pagamentoArmazenado.getDataValidade())
                    .isEqualTo(pagamento.getDataValidade());
            assertThat(pagamentoArmazenado.getCvv())
                    .isEqualTo(pagamento.getCvv());
            assertThat(pagamentoArmazenado.getDescricao())
                    .isEqualTo(pagamento.getDescricao());
            assertThat(pagamentoArmazenado.getMetodoPagamento())
                    .isEqualTo(pagamento.getMetodoPagamento());
            assertThat(pagamentoArmazenado.getStatus())
                    .isEqualTo(pagamento.getStatus());
            assertThat(pagamentoArmazenado.getValor())
                    .isEqualTo(pagamento.getValor());
        });
    }

    @Test
    void devePermitirConsultarPagamentoPorCpfENumero() {
        var cpf = "12345678901";
        var numero = "1234567890123456";
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setCpf(cpf);
        pagamento.setNumero(numero);

        var pagamentoList = List.of(pagamento);

        when(pagamentoDbEntityRepository.findByCpfAndNumero(
                any(String.class),
                any(String.class)))
                    .thenReturn(pagamentoList);

        var pagamentoDbEntityList =
                pagamentoDbEntityRepository.findByCpfAndNumero(cpf, numero);

        verify(pagamentoDbEntityRepository, times(1))
                .findByCpfAndNumero(cpf, numero);
        assertThat(pagamentoDbEntityList)
                .isNotNull()
                .hasSize(1)
                .allSatisfy(pagamentoSalvo -> {
                    assertThat(pagamentoSalvo.getId())
                            .isEqualTo(pagamento.getId());
                    assertThat(pagamentoSalvo.getIdCartao())
                            .isEqualTo(pagamento.getIdCartao());
                    assertThat(pagamentoSalvo.getIdCliente())
                            .isEqualTo(pagamento.getIdCliente());
                    assertThat(pagamentoSalvo.getCpf())
                            .isEqualTo(pagamento.getCpf());
                    assertThat(pagamentoSalvo.getNumero())
                            .isEqualTo(pagamento.getNumero());
                    assertThat(pagamentoSalvo.getDataValidade())
                            .isEqualTo(pagamento.getDataValidade());
                    assertThat(pagamentoSalvo.getCvv())
                            .isEqualTo(pagamento.getCvv());
                    assertThat(pagamentoSalvo.getDescricao())
                            .isEqualTo(pagamento.getDescricao());
                    assertThat(pagamentoSalvo.getMetodoPagamento())
                            .isEqualTo(pagamento.getMetodoPagamento());
                    assertThat(pagamentoSalvo.getStatus())
                            .isEqualTo(pagamento.getStatus());
                    assertThat(pagamentoSalvo.getValor())
                            .isEqualTo(pagamento.getValor());
                });
    }

    @Test
    void devePermitirConsultarPagamentoPorIdCliente() {
        var idCliente = UUID.randomUUID();
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setIdCliente(idCliente);

        var pagamentoList = List.of(pagamento);

        when(pagamentoDbEntityRepository.findByIdCliente(any(UUID.class)))
                .thenReturn(pagamentoList);

        var pagamentoDbEntityList =
                pagamentoDbEntityRepository.findByIdCliente(idCliente);

        verify(pagamentoDbEntityRepository, times(1))
                .findByIdCliente(idCliente);
        assertThat(pagamentoDbEntityList)
            .isNotNull()
            .hasSize(1)
            .allSatisfy(pagamentoSalvo -> {
                assertThat(pagamentoSalvo.getId())
                        .isEqualTo(pagamento.getId());
                assertThat(pagamentoSalvo.getIdCartao())
                        .isEqualTo(pagamento.getIdCartao());
                assertThat(pagamentoSalvo.getIdCliente())
                        .isEqualTo(pagamento.getIdCliente());
                assertThat(pagamentoSalvo.getCpf())
                        .isEqualTo(pagamento.getCpf());
                assertThat(pagamentoSalvo.getNumero())
                        .isEqualTo(pagamento.getNumero());
                assertThat(pagamentoSalvo.getDataValidade())
                        .isEqualTo(pagamento.getDataValidade());
                assertThat(pagamentoSalvo.getCvv())
                        .isEqualTo(pagamento.getCvv());
                assertThat(pagamentoSalvo.getDescricao())
                        .isEqualTo(pagamento.getDescricao());
                assertThat(pagamentoSalvo.getMetodoPagamento())
                        .isEqualTo(pagamento.getMetodoPagamento());
                assertThat(pagamentoSalvo.getStatus())
                        .isEqualTo(pagamento.getStatus());
                assertThat(pagamentoSalvo.getValor())
                        .isEqualTo(pagamento.getValor());
            });
    }

    @Test
    void devePermitirApagarPagamento() {

        var id = UUID.randomUUID();
        doNothing().when(pagamentoDbEntityRepository).deleteById(id);

        pagamentoDbEntityRepository.deleteById(id);

        verify(pagamentoDbEntityRepository, times(1)).deleteById(id);
    }
}