package br.com.fiap.hackaton.pagamentos.application.gateway;

import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.CartaoMessagingGateway;
import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto.CartaoDto;
import br.com.fiap.hackaton.pagamentos.infrastructure.helper.test.CartaoHelper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BuscaCartaoDbGatewayTest {

    @Mock
    private CartaoMessagingGateway cartaoMessagingGateway;

    @Test
    public void devePermitirBuscarPorCpfENumero() {

        var cpf = "";
        var numero = "";
        var cartao = CartaoHelper.gerarCartaoDto();
        var response = ResponseEntity.ok(cartao);

        when(cartaoMessagingGateway.buscarPorCpfENumero(
                any(String.class),
                any(String.class)))
                .thenReturn(response);

        ResponseEntity<CartaoDto> cartaoSalvo =
                this.cartaoMessagingGateway.buscarPorCpfENumero(cpf,numero);


        assertThat(cartaoSalvo).isInstanceOf(CartaoDto.class);

        verify(cartaoMessagingGateway, times(1))
                .buscarPorCpfENumero(cpf,numero);

    }
}
