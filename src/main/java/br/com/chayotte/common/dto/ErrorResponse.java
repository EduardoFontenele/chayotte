package br.com.chayotte.common.dto;

import java.util.List;

public record ErrorResponse(
        int httpStatus,
        List<String> errors
) {
}
