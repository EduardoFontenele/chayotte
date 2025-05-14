package br.com.chayotte.user.usecases;

import br.com.chayotte.common.exception.ApiException;
import br.com.chayotte.user.config.KeycloakConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakAdminService {
    private final Keycloak keycloakAdmin;
    private final KeycloakConfig config;

    public String createUserInKeycloak(UserRepresentation user) {
        var usersResource = keycloakAdmin.realm(config.getTargetRealm()).users();
        log.debug("Criando usuário {} no realm {}", user.getUsername(), config.getTargetRealm());

        try (var response = usersResource.create(user)) {
            if (response.getStatus() != 201) {
                log.error("Falha na criação do usuário {}. Status HTTP: {}",
                        user.getUsername(), response.getStatus());
                throw new ApiException(
                        HttpStatus.valueOf(response.getStatus()),
                        "Falha ao criar usuário. Status: " + response.getStatus()
                );
            }

            var locationHeader = response.getHeaderString("Location");
            if (locationHeader == null) {
                log.error("Header Location não encontrado na resposta de criação do usuário");
                throw new ApiException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Não foi possível obter o ID do usuário criado"
                );
            }

            String userId = locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
            log.debug("Usuário criado com ID: {}", userId);
            return userId;
        }
    }

    public void setUserPassword(String userId, String password) {
        try {
            log.debug("Configurando senha para usuário ID: {}", userId);
            var credential = new CredentialRepresentation();
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(password);
            credential.setTemporary(false);

            keycloakAdmin.realm(config.getTargetRealm())
                    .users()
                    .get(userId)
                    .resetPassword(credential);
            log.debug("Senha configurada com sucesso para o usuário ID: {}", userId);
        } catch (Exception ex) {
            log.error("Erro ao configurar senha para usuário ID: {}", userId, ex);
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao configurar senha do usuário: " + ex.getMessage()
            );
        }
    }

    public String getClientIdByClientName(String clientName) {
        try {
            log.debug("Buscando cliente com nome: {}", clientName);
            var clients = keycloakAdmin.realm(config.getTargetRealm()).clients().findByClientId(clientName);
            if (clients.isEmpty()) {
                log.error("Cliente '{}' não encontrado no realm", clientName);
                throw new ApiException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado: " + clientName
                );
            }
            String clientId = clients.get(0).getId();
            log.debug("Cliente '{}' encontrado com ID: {}", clientName, clientId);
            return clientId;
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Erro ao buscar cliente: {}", clientName, ex);
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro ao buscar cliente: " + ex.getMessage()
            );
        }
    }

    public void assignClientRolesToUser(String userId, String clientId, List<String> roleNames) {
        try {
            log.debug("Atribuindo roles {} do cliente {} ao usuário {}",
                    roleNames, clientId, userId);

            var realmResource = keycloakAdmin.realm(config.getTargetRealm());
            var userResource = realmResource.users().get(userId);

            var rolesToAssign = roleNames.stream()
                    .map(roleName -> {
                        try {
                            return realmResource.clients().get(clientId).roles().get(roleName).toRepresentation();
                        } catch (Exception ex) {
                            log.error("Role '{}' não encontrada para o cliente {}", roleName, clientId, ex);
                            throw new ApiException(
                                    HttpStatus.NOT_FOUND,
                                    "Role '" + roleName + "' não encontrada para o cliente especificado"
                            );
                        }
                    })
                    .toList();

            userResource.roles().clientLevel(clientId).add(rolesToAssign);
            log.info("Roles {} atribuídas com sucesso ao usuário {}", roleNames, userId);
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Erro ao atribuir roles ao usuário: {}", userId, ex);
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Falha ao atribuir roles ao usuário: " + ex.getMessage()
            );
        }
    }
}
