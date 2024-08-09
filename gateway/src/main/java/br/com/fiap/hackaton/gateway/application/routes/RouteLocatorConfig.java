package br.com.fiap.hackaton.gateway.application.routes;



import br.com.fiap.hackaton.gateway.infrastructure.autenticacao.AutenticacaoConfig;
import br.com.fiap.hackaton.gateway.infrastructure.cartao.CartaoConfig;
import br.com.fiap.hackaton.gateway.infrastructure.cliente.ClienteConfig;
import br.com.fiap.hackaton.gateway.infrastructure.pagamentos.PagamentoConfig;
import br.com.fiap.hackaton.gateway.infrastructure.security.route.RouteGuardFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteLocatorConfig {

    private final PagamentoConfig pagamentoConfig;
    private final AutenticacaoConfig autenticacaoConfig;
    private final ClienteConfig clienteConfig;
    private final CartaoConfig cartaoConfig;
    private final RouteGuardFilter routeGuardFilter;

    public RouteLocatorConfig(
            PagamentoConfig pagamentoConfig,
            AutenticacaoConfig autenticacaoConfig,
            ClienteConfig clienteConfig,
            CartaoConfig cartaoConfig,
            RouteGuardFilter routeGuardFilter) {
        this.pagamentoConfig = pagamentoConfig;
        this.autenticacaoConfig = autenticacaoConfig;
        this.clienteConfig = clienteConfig;
        this.cartaoConfig = cartaoConfig;
        this.routeGuardFilter = routeGuardFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("cartao", r -> r.path("/api/cartao/**")
                        .filters(f -> {
                            f.filter(routeGuardFilter);
                            return f.stripPrefix(1);
                        })
                        .uri(cartaoConfig.getUrl()))
                .route("autenticacao", r -> r.path("/api/autenticacao/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(autenticacaoConfig.getUrl()))
                .route("cliente", r -> r.path("/api/cliente/**")
                        .filters(f -> {
                            f.filter(routeGuardFilter);
                            return f.stripPrefix(1);
                        })
                        .uri(clienteConfig.getUrl()))
                .route("pagamentos", r -> r.path("/api/pagamentos/**")
                        .filters(f -> {
                            f.filter(routeGuardFilter);
                            return f.stripPrefix(1);
                        })
                        .uri(this.pagamentoConfig.getUrl()))
                .build();
    }
}
