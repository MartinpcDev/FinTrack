package com.fintrack.api.service;

import com.fintrack.api.persistence.dto.request.CategoryRequest;
import com.fintrack.api.persistence.dto.response.CategoryResponse;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import java.util.List;

public interface CategoryService {

  List<CategoryResponse> findAll();

  CategoryResponse findById(Long id);

  CategoryResponse create(CategoryRequest request);

  CategoryResponse update(Long id, CategoryRequest request);

  GenericResponse delete(Long id);
}
