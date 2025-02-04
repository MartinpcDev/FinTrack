package com.fintrack.api.controller;

import com.fintrack.api.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
@PreAuthorize("isAuthenticated()")
public class CategoryController {

  //TODO: Implement the CategoryController
  private final CategoryService categoryService;
}
