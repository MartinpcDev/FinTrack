package com.fintrack.api.service;

import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.dto.response.PaginationResponse;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

  PaginationResponse<TransactionResponse> findAll(Pageable pageable);

  PaginationResponse<TransactionResponse> findByUserId(Long userId, Pageable pageable);

  TransactionResponse findById(Long id);

  TransactionResponse create(TransactionRequest request);

  TransactionResponse update(Long id, TransactionRequest request);

  GenericResponse delete(Long id);
}
