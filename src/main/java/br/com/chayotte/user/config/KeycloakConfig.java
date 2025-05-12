package br.com.chayotte.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "keycloak.admin")
@Data
public class KeycloakConfig {
    private String serverUrl;
    private String realm;
    private String username;
    private String password;
    private String clientId;
    private String targetRealm;
}

