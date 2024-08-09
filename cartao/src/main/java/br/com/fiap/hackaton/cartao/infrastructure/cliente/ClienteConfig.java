package br.com.fiap.hackaton.cartao.infrastructure.cliente;


import br.com.fiap.hackaton.cartao.infrastructure.cliente.dto.ClienteDto;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Configuration
@ConfigurationProperties(prefix = "hackaton.cliente.api")
public class ClienteConfig {

    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    public MessageChannel buscarPorCpf() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow buscarPorCpfFlow() {
        return IntegrationFlow.from("buscarPorCpf")
                .handle(Http.outboundGateway(String.format("%s/cliente?cpf={cpf}",this.url))
                        .uriVariable("cpf", Message::getPayload)
                        .charset("UTF-8")
                        .extractResponseBody(false)
                        .expectedResponseType(ClienteDto.class)
                        .httpMethod(HttpMethod.GET))
                .log().bridge()
                .get();
    }

}
