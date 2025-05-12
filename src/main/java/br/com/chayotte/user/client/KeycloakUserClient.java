package br.com.chayotte.user.client;

import br.com.chayotte.user.dto.UserCreateRequest;
import br.com.chayotte.user.usecases.KeycloakAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserClient {
    private final KeycloakAdminService keycloakAdminService;

    public String createUser(UserCreateRequest request) {
        try {
            var userRepresentation = mapToUserRepresentation(request);
            var userId = keycloakAdminService.creteUserInKeycloak(userRepresentation);
            keycloakAdminService.setUserPassword(userId, request.password());
            if(!isNull(request.roles()) && !request.roles().isEmpty()) {
                keycloakAdminService.assignRolesToUser(userId, request.roles());
            }
            return userId;
        } catch (Exception ex) {
            log.error("Fuck");
            throw new RuntimeException("aaaa");
        }
    }

    private UserRepresentation mapToUserRepresentation(UserCreateRequest request) {
        var user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmailVerified(request.emailVerified());
        user.setEnabled(request.enabled());
        return user;
    }
}
