package com.fintrack.api.controller;

import com.fintrack.api.persistence.dto.request.CategoryRequest;
import com.fintrack.api.persistence.dto.response.CategoryResponse;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/categories")
@PreAuthorize("isAuthenticated()")
public class CategoryController {
  
  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryResponse>> findAll() {
    return ResponseEntity.ok(categoryService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.findById(id));
  }

  @PostMapping
  public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(categoryService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoryResponse> update(
      @PathVariable Long id,
      @Valid @RequestBody CategoryRequest request) {
    return ResponseEntity.ok(categoryService.update(id, request));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<GenericResponse> delete(@PathVariable Long id) {
    return ResponseEntity.ok(categoryService.delete(id));
  }
}
