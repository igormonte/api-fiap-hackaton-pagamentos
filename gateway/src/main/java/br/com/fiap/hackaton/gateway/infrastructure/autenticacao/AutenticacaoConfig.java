package br.com.fiap.hackaton.gateway.infrastructure.autenticacao;


import br.com.fiap.hackaton.gateway.infrastructure.autenticacao.dto.CheckDto;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Configuration
@ConfigurationProperties(prefix = "hackaton.autenticacao.api")
public class AutenticacaoConfig {

    @NotNull
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bean
    public MessageChannel check() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow checkFlow() {
        return IntegrationFlow.from("check")
                .handle(Http.outboundGateway(String.format("%s/autenticacao/check",this.url))
                        .charset("UTF-8")
                        .extractResponseBody(false)
                        .expectedResponseType(CheckDto.class)
                        .httpMethod(HttpMethod.GET))
                .log().bridge()
                .get();
    }

}
