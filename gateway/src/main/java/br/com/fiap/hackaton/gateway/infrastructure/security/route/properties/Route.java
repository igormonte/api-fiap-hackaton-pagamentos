package br.com.fiap.hackaton.gateway.infrastructure.security.route.properties;

import br.com.postechfiap.carrinhocompra_gateway.infrastructure.security.user.Role;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

public class Route {

    @NotNull
    private String path;

    @Nullable
    private Boolean secured;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean isSecured() {
        if(this.secured == null) {
            return false;
        }
        return secured;
    }

    public void setSecured(Boolean secured) {
        this.secured = secured;
    }
}
