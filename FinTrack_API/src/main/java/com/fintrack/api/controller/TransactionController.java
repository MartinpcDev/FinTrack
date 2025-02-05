package com.fintrack.api.controller;

import com.fintrack.api.persistence.dto.request.TransactionRequest;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.dto.response.PaginationResponse;
import com.fintrack.api.persistence.dto.response.TransactionResponse;
import com.fintrack.api.persistence.model.User;
import com.fintrack.api.service.TransactionService;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
@PreAuthorize("isAuthenticated()")
public class TransactionController {

  private final TransactionService transactionService;

  @GetMapping
  public ResponseEntity<PaginationResponse<TransactionResponse>> findAll(
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    return ResponseEntity.ok(transactionService.findAll(pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(transactionService.findById(id));
  }

  @GetMapping("/category/{categoryName}")
  public ResponseEntity<List<TransactionResponse>> findAllByCategoryName(
      @PathVariable String categoryName) {
    return ResponseEntity.ok(transactionService.findAllByCategoryName(categoryName));
  }

  @GetMapping("/all-by-user")
  public ResponseEntity<List<TransactionResponse>> findAllByUserId(
      @AuthenticationPrincipal UserDetails userDetails) {
    Long userId = ((User) userDetails).getId();
    return ResponseEntity.ok(transactionService.findAllByUserId(userId));
  }

  @GetMapping("/user")
  public ResponseEntity<PaginationResponse<TransactionResponse>> findByUserId(
      @AuthenticationPrincipal UserDetails userDetails,
      @ParameterObject @PageableDefault(sort = {"id"}) Pageable pageable) {
    Long userId = ((User) userDetails).getId();
    return ResponseEntity.ok(transactionService.findByUserId(userId, pageable));
  }

  @PostMapping
  public ResponseEntity<Map<String, Object>> create(
      @Valid @RequestBody TransactionRequest request,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long userId = extractUserId(userDetails);
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Transaction created successfully");
    response.put("data", transactionService.create(request, userId));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Map<String, Object>> update(
      @PathVariable Long id,
      @Valid @RequestBody TransactionRequest request,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long userId = extractUserId(userDetails);
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Transaction updated successfully");
    response.put("data", transactionService.update(id, request, userId));

    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse> delete(@PathVariable Long id,
      @AuthenticationPrincipal UserDetails userDetails) {
    Long userId = extractUserId(userDetails);
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(transactionService.delete(id, userId));
  }

  private Long extractUserId(UserDetails userDetails) {
    return ((User) userDetails).getId();
  }
}
