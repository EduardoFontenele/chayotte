package br.com.chayotte.common.dto;

import java.util.List;

public record ValidationErrorResponse(
        int httpStatus,
        List<String> errors
) {
}
