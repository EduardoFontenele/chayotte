package br.com.chayotte.user.dto;

public record LoginResponse(
        String token,
        Integer expiresIn
) {
}
