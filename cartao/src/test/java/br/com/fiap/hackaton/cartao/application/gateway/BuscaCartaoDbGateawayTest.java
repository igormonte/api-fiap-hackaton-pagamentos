package br.com.fiap.hackaton.cartao.application.gateway;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.entity.NumeroCartao;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepository;
import br.com.fiap.hackaton.cartao.infrastructure.helper.CartaoHelper;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class BuscaCartaoDbGateawayTest {

    private BuscaCartaoRepository buscaCartaoRepository;
    @Mock
    private CartaoDbEntityRepository cartaoDbEntityRepository;
    @Mock
    private CartaoMapper cartaoMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        buscaCartaoRepository = new BuscaCartaoDbGateaway(
                cartaoDbEntityRepository,
                cartaoMapper);
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
            var cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
            cartaoEsperado.setCpf(cpf);
            cartaoEsperado.setNumero(numero);

            var cartaoMapeado = CartaoHelper.gerarCartao();
            cartaoMapeado.setId(cartaoEsperado.getId());
            cartaoMapeado.setCpf(cpf);
            cartaoMapeado.setNumero(NumeroCartao.of(numero));

            when(cartaoDbEntityRepository.findByCpfAndNumero(anyString(), anyString()))
                    .thenReturn(Optional.of(cartaoEsperado));
            when(cartaoMapper.toCartao(any(CartaoDbEntity.class)))
                    .thenReturn(cartaoMapeado);

            var cartaoRetornado = buscaCartaoRepository.porCpfENumeroCartao(cpf, numero);

            assertThat(cartaoRetornado).isPresent();
            cartaoRetornado.ifPresent(cartao-> {
                assertThat(cartao)
                        .extracting(Cartao::getNumero)
                        .isEqualTo(cartaoMapeado.getNumero());
                assertThat(cartao)
                        .extracting(Cartao::getDataValidade)
                        .isEqualTo(cartaoMapeado.getDataValidade());
                assertThat(cartao)
                        .extracting(Cartao::getIdCliente)
                        .isEqualTo(cartaoMapeado.getIdCliente());
                assertThat(cartao)
                        .extracting(Cartao::getCpf)
                        .isEqualTo(cartaoMapeado.getCpf());
                assertThat(cartao)
                        .extracting(Cartao::getId)
                        .isEqualTo(cartaoMapeado.getId());
                assertThat(cartao)
                        .extracting(Cartao::getCvv)
                        .isEqualTo(cartaoMapeado.getCvv());
                assertThat(cartao)
                        .extracting(Cartao::getLimite)
                        .isEqualTo(cartaoMapeado.getLimite());
            });
            verify(cartaoDbEntityRepository, times(1)).findByCpfAndNumero(cpf, numero);
        }

        @Test
        void deveRetornarVazio_QuandoCpfENumeroInvalidos() {
            var cpf = "12345678900";
            var numero = "1234-5678-9012-3456";

            when(cartaoDbEntityRepository.findByCpfAndNumero(anyString(), anyString()))
                    .thenReturn(Optional.empty());

            var resultado = buscaCartaoRepository.porCpfENumeroCartao(cpf, numero);

            assertThat(resultado).isEmpty();
            verify(cartaoDbEntityRepository, times(1)).findByCpfAndNumero(cpf, numero);
        }
    }

    @Nested
    class PorNumeroCartao {

        @Test
        void deveRetornarCartao_QuandoNumeroValido() {
            var numero = "1234-5678-9012-3456";

            var cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
            cartaoEsperado.setNumero(numero);

            var cartaoMapeado = CartaoHelper.gerarCartao();
            cartaoMapeado.setId(cartaoEsperado.getId());
            cartaoMapeado.setNumero(NumeroCartao.of(numero));

            when(cartaoDbEntityRepository.findByNumero(anyString()))
                    .thenReturn(Optional.of(cartaoEsperado));

            when(cartaoMapper.toCartao(any(CartaoDbEntity.class)))
                    .thenReturn(cartaoMapeado);

            var cartaoRetornado = buscaCartaoRepository.porNumeroCartao(numero);

            assertThat(cartaoRetornado).isPresent();
            cartaoRetornado.ifPresent(cartao-> {
                assertThat(cartao)
                        .extracting(Cartao::getNumero)
                        .isEqualTo(cartaoMapeado.getNumero());
                assertThat(cartao)
                        .extracting(Cartao::getDataValidade)
                        .isEqualTo(cartaoMapeado.getDataValidade());
                assertThat(cartao)
                        .extracting(Cartao::getIdCliente)
                        .isEqualTo(cartaoMapeado.getIdCliente());
                assertThat(cartao)
                        .extracting(Cartao::getCpf)
                        .isEqualTo(cartaoMapeado.getCpf());
                assertThat(cartao)
                        .extracting(Cartao::getId)
                        .isEqualTo(cartaoMapeado.getId());
                assertThat(cartao)
                        .extracting(Cartao::getCvv)
                        .isEqualTo(cartaoMapeado.getCvv());
                assertThat(cartao)
                        .extracting(Cartao::getLimite)
                        .isEqualTo(cartaoMapeado.getLimite());
            });
            verify(cartaoDbEntityRepository, times(1)).findByNumero(numero);
        }

        @Test
        void deveRetornarVazio_QuandoNumeroInvalido() {
            var numero = "1234-5678-9012-3456";

            when(cartaoDbEntityRepository.findByNumero(anyString()))
                    .thenReturn(Optional.empty());

            var resultado = buscaCartaoRepository.porNumeroCartao(numero);

            assertThat(resultado).isEmpty();
            verify(cartaoDbEntityRepository, times(1)).findByNumero(numero);
        }
    }

    @Nested
    class PorCpf {

        @Test
        void deveRetornarListaDeCartoes_QuandoCpfValido() {
            var cpf = "12345678900";
            CartaoDbEntity cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
            cartaoEsperado.setCpf(cpf);
            CartaoDbEntity cartaoEsperado2 = CartaoHelper.gerarCartaoDbEntity();
            cartaoEsperado2.setCpf(cpf);

            Cartao cartaoMapeado = CartaoHelper.gerarCartao();
            cartaoMapeado.setId(cartaoEsperado.getId());
            cartaoMapeado.setCpf(cpf);

            Cartao cartaoMapeado2 = CartaoHelper.gerarCartao();
            cartaoMapeado2.setId(cartaoEsperado2.getId());
            cartaoMapeado2.setCpf(cpf);

            List<CartaoDbEntity> cartoesEsperados = List.of(cartaoEsperado, cartaoEsperado2);
            List<Cartao> cartoesMapeados = List.of(cartaoMapeado, cartaoMapeado2);

            when(cartaoDbEntityRepository.findByCpf(anyString()))
                    .thenReturn(cartoesEsperados);

            when(cartaoMapper.toCartaoList(any(List.class)))
                    .thenReturn(cartoesMapeados);

            var resultado = buscaCartaoRepository.porCpf(cpf);

            assertThat(resultado).hasSize(2);
            assertThat(resultado).containsExactlyInAnyOrder(cartaoMapeado, cartaoMapeado2);
            verify(cartaoDbEntityRepository, times(1)).findByCpf(cpf);
        }

        @Test
        void deveRetornarListaVazia_QuandoCpfSemCartoes() {
            var cpf = "12345678900";

            when(cartaoDbEntityRepository.findByCpf(anyString()))
                    .thenReturn(Collections.emptyList());

            var resultado = buscaCartaoRepository.porCpf(cpf);

            assertThat(resultado).isEmpty();
            verify(cartaoDbEntityRepository, times(1)).findByCpf(cpf);
        }
    }
}
