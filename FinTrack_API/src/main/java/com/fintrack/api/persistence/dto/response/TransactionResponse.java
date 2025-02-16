package com.fintrack.api.persistence.dto.response;

import com.fintrack.api.persistence.model.TransactionType;
import java.time.LocalDateTime;

public record TransactionResponse(
    Long id,
    TransactionType type,
    Double amount,
    CategoryResponse category,
    LocalDateTime createdAt
) {

}
