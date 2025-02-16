package com.fintrack.api.service;

import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.dto.response.PaginationResponse;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

  PaginationResponse<TransactionResponse> findAll(Pageable pageable);

  PaginationResponse<TransactionResponse> findByUserId(Long userId, Pageable pageable);

  List<TransactionResponse> findAllByType(Long userId, String type);

  Double getGastoTotalPorMes(Long userId, Integer mes, Integer anio);

  List<TransactionResponse> getGastosPorMes(Long userId, Integer mes, Integer anio);

  List<TransactionResponse> findAllByCategoryName(String categoryName);

  List<TransactionResponse> findAllByUserId(Long userId);

  TransactionResponse findById(Long id);

  TransactionResponse create(TransactionRequest request, Long userId);

  TransactionResponse update(Long id, TransactionRequest request, Long userId);

  GenericResponse delete(Long id, Long userId);
}
