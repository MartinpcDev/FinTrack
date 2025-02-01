package com.fintrack.api.persistence.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
    @NotBlank(message = "El username es requerido")
    String username,
    @NotBlank(message = "La password es requerida")
    String password
) {

}
