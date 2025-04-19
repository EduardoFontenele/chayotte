package br.com.chayotte.common.dto;

public record CreatedDto<T>(
        T response,
        String _self) {
}
