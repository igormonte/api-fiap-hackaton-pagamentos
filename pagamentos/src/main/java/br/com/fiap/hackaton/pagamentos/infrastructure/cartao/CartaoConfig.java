package br.com.fiap.hackaton.pagamentos.infrastructure.cartao;

import br.com.fiap.hackaton.pagamentos.infrastructure.cartao.dto.CartaoDto;
import jakarta.validation.constraints.NotBlank;
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
@ConfigurationProperties(prefix = "hackaton.cartao.api")
public class CartaoConfig {

    @NotBlank
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    public MessageChannel buscarPorCpfENumero() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow buscarPorCpfENumeroFlow() {
        return IntegrationFlow.from("buscarPorCpfENumero")
                .handle(Http.outboundGateway(String.format("%s/cartao?cpf={cpf}&numero={numero}",this.url))
                        .uriVariable("cpf", (m) -> m.getPayload().toString().split(",")[0])
                        .uriVariable("numero", (m) -> m.getPayload().toString().split(",")[1])
                        .charset("UTF-8")
                        .extractResponseBody(false)
                        .expectedResponseType(CartaoDto.class)
                        .httpMethod(HttpMethod.GET))
                .log().bridge()
                .get();
    }

}
