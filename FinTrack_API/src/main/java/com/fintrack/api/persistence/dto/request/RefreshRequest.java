package com.fintrack.api.persistence.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
    @JsonProperty("refresh_token")
    @NotBlank(message = "El refresh_token es requerido")
    String refreshToken
) {

}
