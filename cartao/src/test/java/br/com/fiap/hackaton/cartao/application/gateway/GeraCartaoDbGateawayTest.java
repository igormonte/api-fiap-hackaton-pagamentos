package br.com.fiap.hackaton.cartao.application.gateway;

import br.com.fiap.hackaton.cartao.domain.cartao.entity.Cartao;
import br.com.fiap.hackaton.cartao.domain.usecases.GerarCartaoUseCaseImpl;
import br.com.fiap.hackaton.cartao.domain.usecases.repository.GerarCartaoRepository;
import br.com.fiap.hackaton.cartao.infrastructure.db.entity.CartaoDbEntity;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepository;
import br.com.fiap.hackaton.cartao.infrastructure.db.repository.CartaoDbEntityRepositoryTest;
import br.com.fiap.hackaton.cartao.infrastructure.helper.CartaoHelper;
import br.com.fiap.hackaton.cartao.infrastructure.helper.ClienteHelper;
import br.com.fiap.hackaton.cartao.infrastructure.mapstruct.mapper.CartaoMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GeraCartaoDbGateawayTest {

    private GerarCartaoRepository gerarCartaoRepository;

    @Mock
    private CartaoDbEntityRepository cartaoDbEntityRepository;

    @Mock
    private CartaoMapper cartaoMapper;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        gerarCartaoRepository = new GeraCartaoDbGateaway(cartaoDbEntityRepository, cartaoMapper);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }
    
    @Test
    void deveGerarCartaoQuandoDadosValidos() {
        Cartao cartaoEnviado = CartaoHelper.gerarCartao();
        CartaoDbEntity cartaoEsperado = CartaoHelper.gerarCartaoDbEntity();
        cartaoEsperado.setId(cartaoEnviado.getId());

        when(cartaoDbEntityRepository.save(any(CartaoDbEntity.class)))
                .thenReturn(cartaoEsperado);
        when(cartaoMapper.toCartaoDbEntity(any(Cartao.class)))
                .thenReturn(cartaoEsperado);
        when(cartaoMapper.toCartao(any(CartaoDbEntity.class)))
                .thenReturn(cartaoEnviado);

        var cartaoGerado = gerarCartaoRepository.executar(cartaoEnviado);

        assertThat(cartaoGerado).isNotNull();
        assertThat(cartaoGerado)
                .extracting(Cartao::getNumero)
                .isEqualTo(cartaoEnviado.getNumero());
        assertThat(cartaoGerado)
                .extracting(Cartao::getDataValidade)
                .isEqualTo(cartaoEnviado.getDataValidade());
        assertThat(cartaoGerado)
                .extracting(Cartao::getIdCliente)
                .isEqualTo(cartaoEnviado.getIdCliente());
        assertThat(cartaoGerado)
                .extracting(Cartao::getCpf)
                .isEqualTo(cartaoEnviado.getCpf());
        assertThat(cartaoGerado)
                .extracting(Cartao::getId)
                .isEqualTo(cartaoEnviado.getId());
        assertThat(cartaoGerado)
                .extracting(Cartao::getCvv)
                .isEqualTo(cartaoEnviado.getCvv());
        assertThat(cartaoGerado)
                .extracting(Cartao::getLimite)
                .isEqualTo(cartaoEnviado.getLimite());
        verify(cartaoDbEntityRepository, times(1)).save(any(CartaoDbEntity.class));
    }
}
