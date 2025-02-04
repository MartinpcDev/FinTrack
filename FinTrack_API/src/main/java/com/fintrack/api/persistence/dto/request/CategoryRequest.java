package com.fintrack.api.persistence.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
    @NotBlank(message = "El name es requerido")
    String name,
    @NotBlank(message = "La description es requerida")
    String description
) {

}
