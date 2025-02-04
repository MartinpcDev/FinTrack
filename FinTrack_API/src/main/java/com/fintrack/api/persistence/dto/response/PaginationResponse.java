package com.fintrack.api.persistence.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record PaginationResponse<T>(
    List<T> content,
    Integer page,
    Integer size,
    Integer total,
    @JsonProperty("total_pages")
    Integer totalPages
) {

  public PaginationResponse {
    if (page < 0) {
      throw new IllegalArgumentException("Page number must be greater than or equal to 0");
    }
    if (size < 1) {
      throw new IllegalArgumentException("Page size must be greater than 0");
    }
    if (total < 0) {
      throw new IllegalArgumentException("Total elements must be greater than or equal to 0");
    }
    if (totalPages < 0) {
      throw new IllegalArgumentException("Total pages must be greater than or equal to 0");
    }
  }
}
