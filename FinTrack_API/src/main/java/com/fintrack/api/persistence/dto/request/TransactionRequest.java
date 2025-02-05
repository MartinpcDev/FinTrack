package com.fintrack.api.persistence.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
    @NotBlank(message = "El type es requerido")
    String type,
    @NotNull(message = "El amount es requerido")
    @Positive(message = "El amount debe ser mayor a 0")
    Double amount,
    @JsonProperty("category_id")
    @NotNull(message = "El category_id es requerido")
    Long categoryId
) {

}
