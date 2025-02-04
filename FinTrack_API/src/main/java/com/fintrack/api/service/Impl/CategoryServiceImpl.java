package com.fintrack.api.service.Impl;

import com.fintrack.api.exception.CategoryDuplicatedException;
import com.fintrack.api.exception.CategoryNotFoundException;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.persistence.dto.request.CategoryRequest;
import com.fintrack.api.persistence.dto.response.CategoryResponse;
import com.fintrack.api.persistence.dto.response.GenericResponse;
import com.fintrack.api.persistence.model.Category;
import com.fintrack.api.persistence.repository.CategoryRepository;
import com.fintrack.api.service.CategoryService;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<CategoryResponse> findAll() {
    List<Category> categories = categoryRepository.findAll();
    return CategoryMapper.toCategoryResponseList(categories);
  }

  @Override
  public CategoryResponse findById(Long id) {
    Category category = this.getCategory(id);
    return CategoryMapper.toCategoryResponse(category);
  }

  private Category getCategory(Long id) {
    return categoryRepository.findById(id)
        .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
  }

  @Override
  @Transactional
  public CategoryResponse create(CategoryRequest request) {
    if (categoryRepository.existsByNameIgnoreCase(request.name())) {
      throw new CategoryDuplicatedException("Category already exists");
    }

    Category category = CategoryMapper.toCategory(request);
    Category savedCategory = categoryRepository.save(category);
    return CategoryMapper.toCategoryResponse(savedCategory);
  }

  @Override
  @Transactional
  public CategoryResponse update(Long id, CategoryRequest request) {
    Category category = this.getCategory(id);
    CategoryMapper.updateCategory(category, request);
    Category updatedCategory = categoryRepository.save(category);
    return CategoryMapper.toCategoryResponse(updatedCategory);
  }

  @Override
  @Transactional
  public GenericResponse delete(Long id) {
    Category category = this.getCategory(id);
    categoryRepository.delete(category);
    return new GenericResponse("Category eliminada correctamente");
  }
}
