package com.fintrack.api.service.Impl;

import com.fintrack.api.exception.CategoryNotFoundException;
import com.fintrack.api.exception.TransactionNotFountException;
import com.fintrack.api.mapper.TransactionMapper;
import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.dto.response.PaginationResponse;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import com.fintrack.api.persistence.model.Category;
import com.fintrack.api.persistence.model.Transaction;
import com.fintrack.api.persistence.repository.CategoryRepository;
import com.fintrack.api.persistence.repository.TransactionRepository;
import com.fintrack.api.service.TransactionService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final CategoryRepository categoryRepository;

  @Override
  public PaginationResponse<TransactionResponse> findAll(Pageable pageable) {
    Page<Transaction> transactionPage = transactionRepository.findAll(pageable);
    List<TransactionResponse> transactionResponse = TransactionMapper
        .toTransactionResponseList(transactionPage.getContent());
    return new PaginationResponse<>(
        transactionResponse,
        transactionPage.getNumber(),
        transactionPage.getSize(),
        (int) transactionPage.getTotalElements(),
        transactionPage.getTotalPages()
    );
  }

  @Override
  public PaginationResponse<TransactionResponse> findByUserId(Long userId, Pageable pageable) {
    Page<Transaction> transactionPage = transactionRepository.findAllByUserId(userId, pageable);
    List<TransactionResponse> transactionResponse = TransactionMapper
        .toTransactionResponseList(transactionPage.getContent());
    return new PaginationResponse<>(
        transactionResponse,
        transactionPage.getNumber(),
        transactionPage.getSize(),
        (int) transactionPage.getTotalElements(),
        transactionPage.getTotalPages()
    );
  }

  @Override
  public TransactionResponse findById(Long id) {
    Transaction transaction = this.getTransaction(id);
    return TransactionMapper.toTransactionResponse(transaction);
  }

  private Transaction getTransaction(Long id) {
    return transactionRepository.findById(id)
        .orElseThrow(() -> new TransactionNotFountException("Transaction not found"));
  }

  private Category getCategory(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
  }

  @Override
  @Transactional
  public TransactionResponse create(TransactionRequest request) {
    Category category = this.getCategory(request.categoryId());
    Transaction transaction = TransactionMapper.toTransaction(request);
    transaction.setCategory(category);
    Transaction savedTransaction = transactionRepository.save(transaction);
    return TransactionMapper.toTransactionResponse(savedTransaction);
  }

  @Override
  @Transactional
  public TransactionResponse update(Long id, TransactionRequest request) {
    Transaction transaction = this.getTransaction(id);
    Category category = this.getCategory(request.categoryId());
    TransactionMapper.updateTransaction(transaction, request);
    transaction.setCategory(category);
    Transaction updatedTransaction = transactionRepository.save(transaction);
    return TransactionMapper.toTransactionResponse(updatedTransaction);
  }

  @Override
  @Transactional
  public GenericResponse delete(Long id) {
    Transaction transaction = this.getTransaction(id);
    transactionRepository.delete(transaction);
    return new GenericResponse("Transaction eliminada correctamente");
  }
}
