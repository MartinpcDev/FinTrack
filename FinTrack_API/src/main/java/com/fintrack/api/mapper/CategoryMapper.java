package com.fintrack.api.mapper;

import com.fintrack.api.persistence.dto.request.CategoryRequest;
import com.fintrack.api.persistence.dto.response.CategoryResponse;
import com.fintrack.api.persistence.model.Category;
import java.util.List;

public class CategoryMapper {

  public static CategoryResponse toCategoryResponse(Category category) {
    if (category == null) {
      return null;
    }

    return new CategoryResponse(
        category.getId(),
        category.getName(),
        category.getDescription()
    );
  }

  public static List<CategoryResponse> toCategoryResponseList(List<Category> categories) {
    return categories.stream()
        .map(CategoryMapper::toCategoryResponse)
        .toList();
  }

  public static Category toCategory(CategoryRequest categoryRequest) {
    if (categoryRequest == null) {
      return null;
    }

    Category category = new Category();
    category.setName(categoryRequest.name());
    category.setDescription(categoryRequest.description());

    return category;
  }

  public static void updateCategory(Category category, CategoryRequest categoryRequest) {

    if (category != null && categoryRequest != null) {
      category.setName(categoryRequest.name());
      category.setDescription(categoryRequest.description());
    }

  }
}
