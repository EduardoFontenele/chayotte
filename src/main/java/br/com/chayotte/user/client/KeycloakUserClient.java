package br.com.chayotte.user.client;

import br.com.chayotte.common.exception.ApiException;
import br.com.chayotte.user.dto.UserCreateRequest;
import br.com.chayotte.user.entity.UserCompanyRole;
import br.com.chayotte.user.usecases.KeycloakAdminService;
import jakarta.ws.rs.WebApplicationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserClient {
    private final KeycloakAdminService keycloakAdminService;

    public String createUser(UserCreateRequest request) {
        try {
            log.info("Iniciando criação de usuário {} no Keycloak", request.username());
            var userRepresentation = mapToUserRepresentation(request);
            var userId = keycloakAdminService.createUserInKeycloak(userRepresentation);

            log.info("Usuário {} criado com sucesso. Configurando senha", request.username());
            keycloakAdminService.setUserPassword(userId, request.password());

            var clientId = keycloakAdminService.getClientIdByClientName("chayotte-app");
            log.info("Atribuindo role 'owner' ao usuário {}", request.username());
            keycloakAdminService.assignClientRolesToUser(userId, clientId, List.of("owner"));

            log.info("Usuário {} criado e configurado com sucesso", request.username());
            return userId;
        } catch (WebApplicationException ex) {
            var status = ex.getResponse().getStatus();
            if (status == 409) {
                log.warn("Conflito ao criar usuário: {} ou email {} já existe",
                        request.username(), request.email());
                throw new ApiException(HttpStatus.CONFLICT,
                        "Usuário ou email já existe no sistema");
            }
            log.error("Erro HTTP {} ao criar usuário no Keycloak: {}",
                    status, ex.getMessage());
            throw new ApiException(HttpStatus.valueOf(status),
                    "Falha ao processar a criação do usuário: " + ex.getMessage());
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar usuário no Keycloak", ex);
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro interno ao processar a criação do usuário");
        }
    }

    private UserRepresentation mapToUserRepresentation(UserCreateRequest request) {
        var user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(true);
        return user;
    }
}
