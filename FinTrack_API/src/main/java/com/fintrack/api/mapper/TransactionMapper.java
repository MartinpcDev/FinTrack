package com.fintrack.api.mapper;

import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import com.fintrack.api.persistence.model.Transaction;
import com.fintrack.api.persistence.model.TransactionType;
import java.util.List;

public class TransactionMapper {

  public static TransactionResponse toTransactionResponse(Transaction transaction) {
    if (transaction == null) {
      return null;
    }

    return new TransactionResponse(
        transaction.getId(),
        transaction.getType(),
        transaction.getAmount(),
        CategoryMapper.toCategoryResponse(transaction.getCategory())
    );
  }

  public static List<TransactionResponse> toTransactionResponseList(
      List<Transaction> transactions) {
    return transactions.stream()
        .map(TransactionMapper::toTransactionResponse)
        .toList();
  }

  public static Transaction toTransaction(TransactionRequest transactionRequest) {
    if (transactionRequest == null) {
      return null;
    }

    Transaction transaction = new Transaction();
    transaction.setType(TransactionType.valueOf(transactionRequest.type()));
    transaction.setAmount(transactionRequest.amount());

    return transaction;
  }

  public static void updateTransaction(Transaction transaction,
      TransactionRequest transactionRequest) {

    if (transaction != null && transactionRequest != null) {
      transaction.setType(TransactionType.valueOf(transactionRequest.type()));
      transaction.setAmount(transactionRequest.amount());
    }

  }
}
