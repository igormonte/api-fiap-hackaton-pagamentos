package br.com.fiap.hackaton.cartao.infrastructure.cliente;

import br.com.fiap.hackaton.cartao.infrastructure.cliente.dto.ClienteDto;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageHeaders;

@MessagingGateway
public interface ClienteMessagingGateway {
    @Gateway(requestChannel = "buscarPorCpf",
            requestTimeout = 5000,
            headers = @GatewayHeader(name = MessageHeaders.REPLY_CHANNEL,
                    expression = "@nullChannel"))
    public ResponseEntity<ClienteDto> buscarPorCpf(String cpf);


}
