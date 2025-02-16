package com.fintrack.api.service.Impl;

import com.fintrack.api.exception.CategoryNotFoundException;
import com.fintrack.api.exception.TransactionNotFountException;
import com.fintrack.api.exception.UserNotFoundException;
import com.fintrack.api.mapper.TransactionMapper;
import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.dto.response.PaginationResponse;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import com.fintrack.api.persistence.model.Category;
import com.fintrack.api.persistence.model.Transaction;
import com.fintrack.api.persistence.model.TransactionType;
import com.fintrack.api.persistence.model.User;
import com.fintrack.api.persistence.repository.CategoryRepository;
import com.fintrack.api.persistence.repository.TransactionRepository;
import com.fintrack.api.persistence.repository.UserRepository;
import com.fintrack.api.service.TransactionService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

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
  public List<TransactionResponse> findAllByType(Long userId, String type) {
    List<Transaction> transactions = transactionRepository.findAllByUserIdAndType(userId,
        TransactionType.valueOf(type));
    return TransactionMapper.toTransactionResponseList(transactions);
  }

  @Override
  public Double getGastoTotalPorMes(Long userId, Integer mes, Integer anio) {
    List<Transaction> transactions = transactionRepository.findAllByUserId(userId);
    return transactions.stream()
        .filter(transaction -> transaction.getCreatedAt().getMonthValue() == mes)
        .filter(transaction -> transaction.getCreatedAt().getYear() == anio)
        .map(Transaction::getAmount)
        .reduce(0.0, Double::sum);
  }

  @Override
  public List<TransactionResponse> getGastosPorMes(Long userId, Integer mes, Integer anio) {
    List<Transaction> transactions = transactionRepository.findAllByUserId(userId).stream()
        .filter(transaction -> transaction.getCreatedAt().getMonthValue() == mes)
        .filter(transaction -> transaction.getCreatedAt().getYear() == anio)
        .toList();
    return TransactionMapper.toTransactionResponseList(transactions);
  }

  @Override
  public List<TransactionResponse> findAllByCategoryName(String categoryName) {
    List<Transaction> responses = transactionRepository.findAllByCategoryName(categoryName);
    return TransactionMapper.toTransactionResponseList(responses);
  }

  @Override
  public List<TransactionResponse> findAllByUserId(Long userId) {
    List<Transaction> responses = transactionRepository.findAllByUserId(userId);
    return TransactionMapper.toTransactionResponseList(responses);
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
  public TransactionResponse create(TransactionRequest request, Long userId) {
    Category category = this.getCategory(request.categoryId());
    Transaction transaction = TransactionMapper.toTransaction(request);
    User user = this.getUser(userId);
    transaction.setCategory(category);
    transaction.setUser(user);
    Transaction savedTransaction = transactionRepository.save(transaction);
    return TransactionMapper.toTransactionResponse(savedTransaction);
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  @Override
  @Transactional
  public TransactionResponse update(Long id, TransactionRequest request, Long userId) {
    Transaction transaction = this.getTransaction(id);
    User user = this.getUser(userId);
    Category category = this.getCategory(request.categoryId());

    if (!transaction.getUser().getId().equals(user.getId())) {
      throw new UserNotFoundException("User not found");
    }

    TransactionMapper.updateTransaction(transaction, request);
    transaction.setCategory(category);
    Transaction updatedTransaction = transactionRepository.save(transaction);
    return TransactionMapper.toTransactionResponse(updatedTransaction);
  }

  @Override
  @Transactional
  public GenericResponse delete(Long id, Long userId) {
    Transaction transaction = this.getTransaction(id);
    User user = this.getUser(userId);

    if (!Objects.equals(transaction.getUser().getId(), user.getId())) {
      throw new UserNotFoundException("User not found");
    }

    transactionRepository.delete(transaction);

    return new GenericResponse("Transaction eliminada correctamente " + id);
  }
}
