package br.com.fiap.hackaton.gateway.infrastructure.autenticacao;

import br.com.fiap.hackaton.gateway.infrastructure.autenticacao.dto.CheckDto;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway
public interface AutenticacaoMessagingGateway {
    @Gateway(requestChannel = "check",
            requestTimeout = 5000,
            headers = @GatewayHeader(name = MessageHeaders.REPLY_CHANNEL,
                    expression = "@nullChannel"))
    public ResponseEntity<CheckDto> check(@Header(value = "Authorization") String authorization, Message<String> body);

}
