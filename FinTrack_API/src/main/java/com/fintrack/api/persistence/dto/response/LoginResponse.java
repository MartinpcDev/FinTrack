package com.fintrack.api.persistence.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LoginResponse(
    @JsonProperty("access_token")
    String accessToken,
    @JsonProperty("refresh_token")
    String refreshToken,
    String message
) {

}
