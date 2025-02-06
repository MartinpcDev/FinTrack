package com.fintrack.api.persistence.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExceptionResponse(
    @JsonProperty("status_code")
    Integer statusCode,
    String error,
    String message,
    List<String> details
) {

}
