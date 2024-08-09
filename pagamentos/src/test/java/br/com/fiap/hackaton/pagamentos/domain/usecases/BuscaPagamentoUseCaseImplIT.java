package br.com.fiap.hackaton.pagamentos.domain.usecases;

import br.com.fiap.hackaton.pagamentos.domain.pagamento.entity.Pagamento;
import br.com.fiap.hackaton.pagamentos.domain.usecases.repository.BuscaPagamentoRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.entity.PagamentoDbEntity;
import br.com.fiap.hackaton.pagamentos.infrastructure.db.repository.PagamentoDbEntityRepository;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.PagamentoHelper;
import br.com.fiap.hackaton.pagamentos.infrastructure.mapstruct.mapper.PagamentoMapper;
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

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class BuscaPagamentoUseCaseImplIT {

    @Autowired
    private BuscaPagamentoUseCase buscaPagamentoUseCase;

    @Autowired
    private PagamentoDbEntityRepository pagamentoDbEntityRepository;

    @Autowired
    private BuscaPagamentoRepository buscaPagamentoRepository;

    @Autowired
    private PagamentoMapper pagamentoMapper;

    @Nested
    class BuscarPorIdCliente {

        @Test
        void devePermitirBuscarPagamentosPorIdCliente() {
            var idCliente = UUID.randomUUID();
            var pagamento = PagamentoHelper.gerarPagamento();
            pagamento.getCartao().setIdCliente(idCliente);

            var resultado = buscaPagamentoUseCase.porIdCliente(idCliente);

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(2)
                    .allSatisfy(pagamentoSalvo -> {
                        assertThat(pagamento).isInstanceOf(Pagamento.class);
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
            var idCliente = UUID.randomUUID();

            var resultado = buscaPagamentoUseCase.porIdCliente(idCliente);

            assertThat(resultado).isEmpty();
            verify(buscaPagamentoRepository, times(1)).porIdCliente(idCliente);
        }
    }

    @Nested
    class BuscarPorCpfENumero {

        @Test
        void devePermitirBuscarPagamentosPorCpfENumero() {
            var cpf = "12345678900";
            var numero = "1234123412341234";
            var pagamento = PagamentoHelper.gerarPagamento();
            pagamento.getCartao().setCpf(cpf);
            pagamento.getCartao().setNumero(numero);
            var resultado = buscaPagamentoUseCase.porCpfENumero(cpf, numero);

            assertThat(resultado)
                    .isNotNull()
                    .hasSize(2)
                    .allSatisfy(pagamentoSalvo -> {
                        assertThat(pagamento).isInstanceOf(Pagamento.class);
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
            var cpf = "12345678900";
            var numero = "1234123412341234";

            var resultado = buscaPagamentoUseCase.porCpfENumero(cpf, numero);

            assertThat(resultado).isEmpty();
            verify(buscaPagamentoRepository, times(1)).porCpfAndNumero(cpf, numero);
        }
    }

    private Pagamento registrarPagamento(
            Pagamento pagamento) {
        return this.pagamentoMapper.toPagamento(
                this.pagamentoDbEntityRepository.save(
                        this.pagamentoMapper.toPagamentoDbEntity(pagamento)));
    }

}

