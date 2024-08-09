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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class PagamentoDbEntityRepositoryIT {
    @Autowired
    private PagamentoDbEntityRepository pagamentoDbEntityRepository;

    @Test
    void devePermitirCriarTabela() {
        long totalTabelasCriada = pagamentoDbEntityRepository.count();
        assertThat(totalTabelasCriada).isNotNegative();
    }

    @Test
    void devePermitirRegistrarPagamento() {
        var id = UUID.randomUUID();
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setId(id);

        var pagamentoArmazenado = pagamentoDbEntityRepository.save(pagamento);

        assertThat(pagamentoArmazenado)
                .isInstanceOf(PagamentoDbEntity.class)
                .isNotNull();
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
                .isEqualByComparingTo(pagamento.getValor());
    }

    @Test
    void devePermitirConsultarPagamentoPorId() {
        var pagamento = registrarPagamento();
        var id = pagamento.getId();

        var pagamentoOptional = pagamentoDbEntityRepository.findById(id);

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
                    .isEqualByComparingTo(pagamento.getValor());
        });
    }
    
    @Test
    void devePermitirConsultarPagamentoPorCpfENumero() {
        var cpf = "12345678901";
        var numero = "1234567890123456";
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        var pagamentoSalvo = registrarPagamento(pagamento);


        var pagamentoDbEntityList =
                pagamentoDbEntityRepository.findByCpfAndNumero(cpf, numero);

        verify(pagamentoDbEntityRepository, times(1))
                .findByCpfAndNumero(cpf, numero);
        assertThat(pagamentoDbEntityList)
                .isNotNull()
                .hasSize(1)
                .allSatisfy(pagamentoItem -> {
                    assertThat(pagamentoItem.getId())
                            .isEqualTo(pagamentoSalvo.getId());
                    assertThat(pagamentoItem.getIdCartao())
                            .isEqualTo(pagamentoSalvo.getIdCartao());
                    assertThat(pagamentoItem.getIdCliente())
                            .isEqualTo(pagamentoSalvo.getIdCliente());
                    assertThat(pagamentoItem.getCpf())
                            .isEqualTo(pagamentoSalvo.getCpf());
                    assertThat(pagamentoItem.getNumero())
                            .isEqualTo(pagamentoSalvo.getNumero());
                    assertThat(pagamentoItem.getDataValidade())
                            .isEqualTo(pagamentoSalvo.getDataValidade());
                    assertThat(pagamentoItem.getCvv())
                            .isEqualTo(pagamentoSalvo.getCvv());
                    assertThat(pagamentoItem.getDescricao())
                            .isEqualTo(pagamentoSalvo.getDescricao());
                    assertThat(pagamentoItem.getMetodoPagamento())
                            .isEqualTo(pagamentoSalvo.getMetodoPagamento());
                    assertThat(pagamentoItem.getStatus())
                            .isEqualTo(pagamentoSalvo.getStatus());
                    assertThat(pagamentoItem.getValor())
                            .isEqualTo(pagamentoSalvo.getValor());
                });
    }

    @Test
    void devePermitirConsultarPagamentoPorIdCliente() {
        var idCliente = UUID.randomUUID();
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        pagamento.setIdCliente(idCliente);
        var pagamentoSalvo = registrarPagamento(pagamento);

        var pagamentoDbEntityList =
                pagamentoDbEntityRepository.findByIdCliente(idCliente);

        verify(pagamentoDbEntityRepository, times(1))
                .findByIdCliente(idCliente);
        assertThat(pagamentoDbEntityList)
                .isNotNull()
                .hasSize(1)
                .allSatisfy(pagamentoItem -> {
                    assertThat(pagamentoItem.getId())
                            .isEqualTo(pagamentoSalvo.getId());
                    assertThat(pagamentoItem.getIdCartao())
                            .isEqualTo(pagamentoSalvo.getIdCartao());
                    assertThat(pagamentoItem.getIdCliente())
                            .isEqualTo(pagamentoSalvo.getIdCliente());
                    assertThat(pagamentoItem.getCpf())
                            .isEqualTo(pagamentoSalvo.getCpf());
                    assertThat(pagamentoItem.getNumero())
                            .isEqualTo(pagamentoSalvo.getNumero());
                    assertThat(pagamentoItem.getDataValidade())
                            .isEqualTo(pagamentoSalvo.getDataValidade());
                    assertThat(pagamentoItem.getCvv())
                            .isEqualTo(pagamentoSalvo.getCvv());
                    assertThat(pagamentoItem.getDescricao())
                            .isEqualTo(pagamentoSalvo.getDescricao());
                    assertThat(pagamentoItem.getMetodoPagamento())
                            .isEqualTo(pagamentoSalvo.getMetodoPagamento());
                    assertThat(pagamentoItem.getStatus())
                            .isEqualTo(pagamentoSalvo.getStatus());
                    assertThat(pagamentoItem.getValor())
                            .isEqualTo(pagamentoSalvo.getValor());
                });
    }

    @Test
    void devePermitirApagarPagamento() {
        var pagamento = registrarPagamento();
        var id = pagamento.getId();

        pagamentoDbEntityRepository.deleteById(id);
        var pagamentoOptional = pagamentoDbEntityRepository.findById(id);

        assertThat(pagamentoOptional).isEmpty();
    }

    private PagamentoDbEntity registrarPagamento(
            PagamentoDbEntity pagamento) {
        return pagamentoDbEntityRepository.save(pagamento);
    }
    private PagamentoDbEntity registrarPagamento() {
        var pagamento = PagamentoHelper.gerarPagamentoDbEntity();
        return pagamentoDbEntityRepository.save(pagamento);
    }
}