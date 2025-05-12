package br.com.chayotte.user.usecases;

import br.com.chayotte.user.client.KeycloakUserClient;
import br.com.chayotte.user.dto.UserCreateRequest;
import br.com.chayotte.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final KeycloakUserClient keycloakClient;

    public UserResponse createUser(UserCreateRequest request) {
        log.info("Processando criação de usuário: {}", request.username());

        validateBusinessRules(request);

        var userId = keycloakClient.createUser(request);

        return new UserResponse(
                userId,
                request.username(),
                request.email(),
                LocalDateTime.now()
        );
    }

    private void validateBusinessRules(UserCreateRequest request) {
        // Exemplo: verificar se username já existe
        // Implementar regras de negócio específicas
    }
}
