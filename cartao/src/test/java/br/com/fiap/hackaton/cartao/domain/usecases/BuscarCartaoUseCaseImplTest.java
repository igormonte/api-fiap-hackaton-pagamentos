package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.NumeroCartao;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import br.com.fiap.hackaton.cartao.infrastructure.helper.CartaoHelper;
import ch.qos.logback.core.model.NamedModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class BuscarCartaoUseCaseImplTest {

    private BuscarCartaoUseCaseImpl buscarCartaoUseCase;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscarCartaoUseCase = new BuscarCartaoUseCaseImpl(buscarCartaoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class PorCpfENumeroCartao {

        @Test
        void deveRetornarCartao_QuandoCpfENumeroValidos() {
            var cpf = "12345678900";
            var numero = "1234-5678-9012-3456";
            var cartao = CartaoHelper.gerarCartao();
            cartao.setCpf(cpf);
            cartao.setNumero(NumeroCartao.of(numero));

            when(buscarCartaoRepository.porCpfENumeroCartao(anyString(), anyString()))
                    .thenReturn(Optional.of(cartao));

            var resultado = buscarCartaoUseCase.porCpfENumeroCartao(cpf, numero);

            assertThat(resultado).isPresent();
            assertThat(resultado.get()).isEqualTo(cartao);
            verify(buscarCartaoRepository, times(1)).porCpfENumeroCartao(cpf, numero);
        }

        @Test
        void deveRetornarVazio_QuandoCpfENumeroInvalidos() {
            var cpf = "12345678900";
            var numero = "1234-5678-9012-3456";

            when(buscarCartaoRepository.porCpfENumeroCartao(anyString(), anyString()))
                    .thenReturn(Optional.empty());

            var resultado = buscarCartaoUseCase.porCpfENumeroCartao(cpf, numero);

            assertThat(resultado).isEmpty();
            verify(buscarCartaoRepository, times(1)).porCpfENumeroCartao(cpf, numero);
        }
    }

    @Nested
    class PorNumeroCartao {

        @Test
        void deveRetornarCartao_QuandoNumeroValido() {
            var numero = "1234-5678-9012-3456";
            var cartao = CartaoHelper.gerarCartao();
            cartao.setNumero(NumeroCartao.of(numero));

            when(buscarCartaoRepository.porNumeroCartao(anyString()))
                    .thenReturn(Optional.of(cartao));

            var resultado = buscarCartaoUseCase.porNumeroCartao(numero);

            assertThat(resultado).isPresent();
            assertThat(resultado.get()).isEqualTo(cartao);
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(numero);
        }

        @Test
        void deveRetornarVazio_QuandoNumeroInvalido() {
            var numero = "1234-5678-9012-3456";

            when(buscarCartaoRepository.porNumeroCartao(anyString()))
                    .thenReturn(Optional.empty());

            var resultado = buscarCartaoUseCase.porNumeroCartao(numero);

            assertThat(resultado).isEmpty();
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(numero);
        }
    }

    @Nested
    class PorCpf {

        @Test
        void deveRetornarListaDeCartoes_QuandoCpfValido() {
            var cpf = "12345678900";
            var cartao1 = new Cartao(UUID.randomUUID(), cpf, new BigDecimal("1000.00"), "1234-5678-9012-3456", "12/26", "123");
            var cartao2 = new Cartao(UUID.randomUUID(), cpf, new BigDecimal("500.00"), "9876-5432-1098-7654", "01/28", "456");
            List<Cartao> cartoes = List.of(cartao1, cartao2);

            when(buscarCartaoRepository.porCpf(anyString()))
                    .thenReturn(cartoes);

            var resultado = buscarCartaoUseCase.porCpf(cpf);

            assertThat(resultado).hasSize(2);
            assertThat(resultado).containsExactlyInAnyOrder(cartao1, cartao2);
            verify(buscarCartaoRepository, times(1)).porCpf(cpf);
        }

        @Test
        void deveRetornarListaVazia_QuandoCpfSemCartoes() {
            var cpf = "12345678900";

            when(buscarCartaoRepository.porCpf(anyString()))
                    .thenReturn(Collections.emptyList());

            var resultado = buscarCartaoUseCase.porCpf(cpf);

            assertThat(resultado).isEmpty();
            verify(buscarCartaoRepository, times(1)).porCpf(cpf);
        }
    }
}

