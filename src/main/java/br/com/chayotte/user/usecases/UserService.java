package br.com.chayotte.user.usecases;

import br.com.chayotte.common.exception.ApiException;
import br.com.chayotte.user.client.KeycloakUserClient;
import br.com.chayotte.user.dto.UserCreateRequest;
import br.com.chayotte.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final KeycloakUserClient keycloakClient;

    public UserResponse createUser(UserCreateRequest request) {
        log.info("Processando criação de usuário: {}", request.username());

        try {
            validateBusinessRules(request);

            var userId = keycloakClient.createUser(request);
            log.info("Usuário {} criado com sucesso com ID: {}", request.username(), userId);

            return new UserResponse(
                    userId,
                    request.username(),
                    request.email(),
                    LocalDateTime.now()
            );
        } catch (ApiException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Erro inesperado ao criar usuário: {}", request.username(), ex);
            throw new ApiException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Erro interno ao processar a criação do usuário: " + ex.getMessage()
            );
        }
    }

    private void validateBusinessRules(UserCreateRequest request) {
        log.debug("Validando regras de negócio para o usuário: {}", request.username());

        if (request.email().endsWith("@exemplo-bloqueado.com")) {
            log.warn("Tentativa de cadastro com domínio de email não permitido: {}", request.email());
            throw new ApiException(
                    HttpStatus.BAD_REQUEST,
                    "Domínio de email não permitido para cadastro"
            );
        }
    }
}
