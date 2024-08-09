package br.com.fiap.hackaton.cartao.domain.usecases;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.CartaoJaCadastradoException;
import br.com.fiap.hackaton.cartao.domain.cartao.exception.NumeroMaximoCartoesAtingidoException;
import br.com.fiap.hackaton.cartao.domain.cliente.entity.Cliente;
import br.com.fiap.hackaton.cartao.domain.cliente.exception.ClienteNaoEncontradoException;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaCartaoRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.BuscaClienteRepository;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.GerarCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.helper.ClienteHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GerarCartaoUseCaseImplTest {

    private GerarCartaoUseCase gerarCartaoUseCase;

    @Mock
    private GerarCartaoRepository gerarCartaoRepository;

    @Mock
    private BuscaClienteRepository buscaClienteRepository;

    @Mock
    private BuscaCartaoRepository buscarCartaoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        gerarCartaoUseCase = new GerarCartaoUseCaseImpl(gerarCartaoRepository, buscaClienteRepository, buscarCartaoRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class GerarPlanoCustomizado {

        @Test
        void deveGerarCartaoQuandoDadosValidos() {
            var cpf = "12345678900";
            var limite = new BigDecimal("1000.00");
            var numero = "1234-5678-9012-3456";
            var dataValidade = "12/26";
            var cvv = "123";

            when(buscarCartaoRepository.porNumeroCartao(any(String.class))).thenReturn(Optional.empty());
            var cliente = ClienteHelper.gerarCliente();
            cliente.setCpf(cpf);
            when(buscaClienteRepository.buscaPorCpf(any(String.class))).thenReturn(Optional.of(cliente));
            when(buscarCartaoRepository.porCpf(any(String.class))).thenReturn(Collections.emptyList());
            var cartao = new Cartao(cliente.getId(), cpf, limite, numero, dataValidade, cvv);
            when(gerarCartaoRepository.executar(any(Cartao.class))).thenReturn(cartao);

            var cartaoGerado = gerarCartaoUseCase.gerarPlanoCustomizado(cpf, limite, numero, dataValidade, cvv);

            assertThat(cartaoGerado).isNotNull();
            assertThat(cartaoGerado.getCpf()).isEqualTo(cpf);
            assertThat(cartaoGerado.getNumero().getNumero()).isEqualTo(numero);
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(any(String.class));
            verify(buscaClienteRepository, times(1)).buscaPorCpf(any(String.class));
            verify(buscarCartaoRepository, times(1)).porCpf(any(String.class));
            verify(gerarCartaoRepository, times(1)).executar(any(Cartao.class));
        }

        @Test
        void deveLancarExcecaoQuandoCartaoJaExistente() {
            var numero = "1234-5678-9012-3456";
            when(buscarCartaoRepository.porNumeroCartao(any(String.class))).thenReturn(Optional.of(new Cartao()));

            assertThatThrownBy(() -> gerarCartaoUseCase.gerarPlanoCustomizado("12345678900", new BigDecimal("1000.00"), numero, "12/26", "123"))
                    .isInstanceOf(CartaoJaCadastradoException.class);
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(any(String.class));
        }

        @Test
        void deveLancarExcecaoQuandoClienteNaoEncontrado() {
            var cpf = "12345678900";
            when(buscarCartaoRepository.porNumeroCartao(any(String.class))).thenReturn(Optional.empty());
            when(buscaClienteRepository.buscaPorCpf(any(String.class))).thenReturn(Optional.empty());

            assertThatThrownBy(() -> gerarCartaoUseCase.gerarPlanoCustomizado(cpf, new BigDecimal("1000.00"), "1234-5678-9012-3456", "12/26", "123"))
                    .isInstanceOf(ClienteNaoEncontradoException.class);
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(any(String.class));
            verify(buscaClienteRepository, times(1)).buscaPorCpf(any(String.class));
        }

        @Test
        void deveLancarExcecaoQuandoNumeroMaximoDeCartoesAtingido() {
            var cpf = "12345678900";
            when(buscarCartaoRepository.porNumeroCartao(any(String.class))).thenReturn(Optional.empty());
            var cliente = ClienteHelper.gerarCliente();
            cliente.setCpf(cpf);
            when(buscaClienteRepository.buscaPorCpf(any(String.class))).thenReturn(Optional.of(cliente));
            var cartoes = List.of(new Cartao(), new Cartao());
            when(buscarCartaoRepository.porCpf(any(String.class))).thenReturn(cartoes);

            assertThatThrownBy(() -> gerarCartaoUseCase.gerarPlanoCustomizado(cpf, new BigDecimal("1000.00"), "1234-5678-9012-3456", "12/26", "123"))
                    .isInstanceOf(NumeroMaximoCartoesAtingidoException.class)
                    .hasMessage("Número máximo de cartões atingido!");
            verify(buscarCartaoRepository, times(1)).porNumeroCartao(any(String.class));
            verify(buscaClienteRepository, times(1)).buscaPorCpf(any(String.class));
            verify(buscarCartaoRepository, times(1)).porCpf(any(String.class));
        }
    }
}
