package br.com.fiap.hackaton.cartao.infrastructure.db.repository;

import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import br.com.fiap.hackaton.cartao.infrastructure.helper.CartaoHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
public class CartaoDbEntityRepositoryTest {

    @Mock
    private CartaoDbEntityRepository cartaoDbEntityRepository;

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
    void devePermitirBuscarPorCpf() {
        // Arrange
        var cpf = "12345678900";
        CartaoDbEntity cartao1 = CartaoHelper.gerarCartaoDbEntity();
        cartao1.setCpf(cpf);
        CartaoDbEntity cartao2 = CartaoHelper.gerarCartaoDbEntity();;
        cartao2.setCpf(cpf);

        var cartoes = List.of(cartao1, cartao2);

        when(cartaoDbEntityRepository.findByCpf(any(String.class))).thenReturn(cartoes);

        var resultado = cartaoDbEntityRepository.findByCpf(cpf);

        verify(cartaoDbEntityRepository, times(1)).findByCpf(cpf);
        assertThat(resultado).hasSize(2).containsExactlyInAnyOrder(cartao1, cartao2);
    }

    @Test
    void devePermitirBuscarPorCpfENumero() {
        // Arrange
        var cpf = "12345678900";
        var numero = "1234-5678-9012-3456";
        var cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
        cartaoEsperado.setCpf(cpf);
        cartaoEsperado.setNumero(numero);

        when(cartaoDbEntityRepository.findByCpfAndNumero(any(String.class), any(String.class)))
                .thenReturn(Optional.of(cartaoEsperado));

        var cartaoRetornado = cartaoDbEntityRepository.findByCpfAndNumero(cpf, numero);


        cartaoRetornado.ifPresent(cartao-> {
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getNumero)
                    .isEqualTo(cartaoEsperado.getNumero());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getDataValidade)
                    .isEqualTo(cartaoEsperado.getDataValidade());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getIdCliente)
                    .isEqualTo(cartaoEsperado.getIdCliente());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getCpf)
                    .isEqualTo(cartaoEsperado.getCpf());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getId)
                    .isEqualTo(cartaoEsperado.getId());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getCvv)
                    .isEqualTo(cartaoEsperado.getCvv());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getLimite)
                    .isEqualTo(cartaoEsperado.getLimite());
        });

        verify(cartaoDbEntityRepository, times(1)).findByCpfAndNumero(cpf, numero);
    }

    @Test
    void devePermitirBuscarPorNumero() {
        // Arrange
        var numero = "1234-5678-9012-3456";
        var cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
        cartaoEsperado.setNumero(numero);
        when(cartaoDbEntityRepository.findByNumero(any(String.class))).thenReturn(Optional.of(cartaoEsperado));

        var cartaoRetornado = cartaoDbEntityRepository.findByNumero(numero);

        cartaoRetornado.ifPresent(cartao-> {
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getNumero)
                    .isEqualTo(cartaoEsperado.getNumero());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getDataValidade)
                    .isEqualTo(cartaoEsperado.getDataValidade());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getIdCliente)
                    .isEqualTo(cartaoEsperado.getIdCliente());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getCpf)
                    .isEqualTo(cartaoEsperado.getCpf());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getId)
                    .isEqualTo(cartaoEsperado.getId());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getCvv)
                    .isEqualTo(cartaoEsperado.getCvv());
            assertThat(cartao)
                    .extracting(CartaoDbEntity::getLimite)
                    .isEqualTo(cartaoEsperado.getLimite());
        });

        verify(cartaoDbEntityRepository, times(1)).findByNumero(numero);

    }

}
