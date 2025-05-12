package br.com.chayotte.user.usecases;

import br.com.chayotte.user.config.KeycloakConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakAdminService {
    private final Keycloak keycloakAdmin;
    private final KeycloakConfig config;

    public String creteUserInKeycloak(UserRepresentation user) {
        var usersResource = keycloakAdmin.realm(config.getTargetRealm()).users();
        try (var response = usersResource.create(user)) {
            if (response.getStatus() != 201) {
                throw new RuntimeException(
                        "Falha ao criar usuário. Status: " + response.getStatus()
                );
            }

            var locationHeader = response.getHeaderString("Location");
            return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
        }
    }

    public void setUserPassword(String userId, String password) {
        var credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(password);
        credential.setTemporary(false);

        keycloakAdmin.realm(config.getTargetRealm())
                .users()
                .get(userId)
                .resetPassword(credential);
    }

    public void assignRolesToUser(String userId, List<String> roleNames) {
        var realmResource = keycloakAdmin.realm(config.getTargetRealm());
        var userResource = realmResource.users().get(userId);

        var rolesToAssign = roleNames.stream()
                .map(roleName -> realmResource.roles().get(roleName).toRepresentation())
                .toList();

        userResource.roles().realmLevel().add(rolesToAssign);
    }
}
