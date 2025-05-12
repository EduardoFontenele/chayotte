package br.com.chayotte.user.controller;

import br.com.chayotte.user.dto.LoginRequest;
import br.com.chayotte.user.dto.LoginResponse;
import br.com.chayotte.user.dto.UserCreateRequest;
import br.com.chayotte.user.dto.UserResponse;
import br.com.chayotte.user.usecases.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.InvalidCredentialsException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("public/api/users")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "User Management", description = "APIs for user registration and authentication")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account in the system",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User data for registration",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserCreateRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data provided",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists",
                    content = @Content)
    })
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@Valid @RequestBody UserCreateRequest user) {
        var response = userService.createUser(user);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(
            summary = "User authentication",
            description = "Authenticates a user using Keycloak and returns an access token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials for authentication",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Invalid username or password",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error during authentication",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) throws InvalidCredentialsException {
        log.info("Tentativa de login para usuário: {}", request.username());

        var restTemplate = new RestTemplate();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var params = new LinkedMultiValueMap<String, String>();
        params.add("client_id", "chayotte-app");
        params.add("client_secret", "K3Vpz05ZXuc1Ody6cF2TuiNufN3MNXB6");
        params.add("grant_type", "password");
        params.add("username", request.username());
        params.add("password", request.password());
        params.add("scope", "openid");

        var requestEntity = new HttpEntity<>(params, headers);

        try {
            var tokenUrl = "http://localhost:8180/realms/chayotte-realm/protocol/openid-connect/token";

            var response = restTemplate.postForEntity(
                    tokenUrl,
                    requestEntity,
                    Map.class
            );

            var responseBody = response.getBody();

            var loginResponse = new LoginResponse(
                    (String) responseBody.get("access_token"),
                    (Integer) responseBody.get("expires_in")
            );

            log.info("Login bem-sucedido para usuário: {}", request.username());
            return ResponseEntity.ok(loginResponse);

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InvalidCredentialsException("Usuário ou senha inválidos");
            }
            throw new RuntimeException("Erro ao autenticar: " + e.getMessage());
        }
    }
}