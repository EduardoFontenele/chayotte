package br.com.chayotte.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/v1/**").authenticated()
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userAuthoritiesMapper(authoritiesMapper())))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .decoder(jwtDecoder())))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation(issuerUri);
    }

    private GrantedAuthoritiesMapper authoritiesMapper() {
        return authorities -> {
            var mappedAuthorities = new HashSet<GrantedAuthority>();

            authorities.forEach(authority -> {
                mappedAuthorities.add(authority);

                if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                    var userInfo = oidcUserAuthority.getUserInfo();

                    var realmAccess = userInfo.getClaimAsMap("realm_access");
                    if (realmAccess != null) {
                        var roles = (List<String>) realmAccess.get("roles");
                        if (roles != null) {
                            roles.forEach(role -> {
                                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                            });
                        }
                    }

                    var resourceAccess = userInfo.getClaimAsMap("resource_access");
                    if (resourceAccess != null) {
                        var clientResource = (Map<String, Object>) resourceAccess.get("chayotte-app");
                        if (clientResource != null) {
                            var clientRoles = (List<String>) clientResource.get("roles");
                            if (clientRoles != null) {
                                clientRoles.forEach(role -> {
                                    mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                                });
                            }
                        }
                    }
                }
            });

            return mappedAuthorities;
        };
    }
}
