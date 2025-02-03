package com.fintrack.api.persistence.dto.response;

public record ProfileResponse(
    Long id,
    String name,
    String username,
    String email
) {

}
