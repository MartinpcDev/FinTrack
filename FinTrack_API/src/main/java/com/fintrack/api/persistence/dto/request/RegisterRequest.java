package com.fintrack.api.persistence.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
    @NotBlank(message = "El nombre es requerido")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    String name,
    @NotBlank(message = "El username es requerido")
    @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres")
    String username,
    @NotBlank(message = "La password es requerida")
    @Size(min = 6, max = 50, message = "La password debe tener entre 6 y 50 caracteres")
    String password,
    @NotBlank(message = "El email es requerido")
    @Email(message = "El email no es válido")
    String email
) {

}
