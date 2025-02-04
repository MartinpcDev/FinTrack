package com.fintrack.api.exception;

public class CategoryDuplicatedException extends RuntimeException {

  public CategoryDuplicatedException(String message) {
    super(message);
  }
}
