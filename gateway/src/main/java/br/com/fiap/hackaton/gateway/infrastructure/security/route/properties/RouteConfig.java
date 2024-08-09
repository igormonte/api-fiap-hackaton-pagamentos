package br.com.fiap.hackaton.gateway.infrastructure.security.route.properties;

import br.com.postechfiap.carrinhocompra_gateway.infrastructure.security.user.Role;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
@Component
@ConfigurationProperties(prefix = "route.config")
public class RouteConfig {
    private List<Route> routes;
    public List<Route> getRoutes() {
        return routes;
    }
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    public Boolean isSecureRoute(String url) {

        Optional<Route> route = this.routes.stream().filter(f->url.contains(f.getPath())).findAny();

        if(route.isEmpty()) {
            return true;
        }

        return route.get().isSecured();
    }
}
