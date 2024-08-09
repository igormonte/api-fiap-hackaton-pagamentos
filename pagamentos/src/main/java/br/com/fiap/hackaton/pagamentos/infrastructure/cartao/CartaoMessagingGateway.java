package br.com.fiap.hackaton.pagamentos.infrastructure.cartao;

import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto.CartaoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Payloads;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

@MessagingGateway
public interface CartaoMessagingGateway {
    @Gateway(requestChannel = "buscarPorCpfENumero",
            requestTimeout = 5000,
            headers = @GatewayHeader(name = MessageHeaders.REPLY_CHANNEL,
                    expression = "@nullChannel"))
    @Payload("args[0] + \",\" + args[1]")
    public ResponseEntity<CartaoDto> buscarPorCpfENumero(String cpf, String numero);


}
