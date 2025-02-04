package com.fintrack.api.persistence.repository;

import com.fintrack.api.persistence.model.Transaction;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  List<Transaction> findAllByCategoryName(String categoryName);

  List<Transaction> findAllByUserId(Long userId);

  Page<Transaction> findAllByUserId(Long userId, Pageable pageable);
}
