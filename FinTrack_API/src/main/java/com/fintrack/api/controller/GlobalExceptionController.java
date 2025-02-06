package com.fintrack.api.controller;

import com.fintrack.api.exception.CategoryDuplicatedException;
import com.fintrack.api.exception.CategoryNotFoundException;
import com.fintrack.api.exception.InvalidAuthException;
import com.fintrack.api.exception.TransactionNotFountException;
import com.fintrack.api.exception.UserNotFoundException;
import com.fintrack.api.persistence.dto.response.ExceptionResponse;
import java.util.List;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException exception) {
    List<ObjectError> errors = exception.getAllErrors();
    List<String> details = errors.stream()
        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        "Validation failed",
        details);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ExceptionResponse> illegalArgumentException(
      IllegalArgumentException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.BAD_REQUEST.value(),
        "Bad Request",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(InvalidAuthException.class)
  public ResponseEntity<ExceptionResponse> handleInvalidAuthException(
      InvalidAuthException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.UNAUTHORIZED.value(),
        "Unauthorized",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(TransactionNotFountException.class)
  public ResponseEntity<ExceptionResponse> handleNotFoundException(
      TransactionNotFountException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFoundException(
      UserNotFoundException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<ExceptionResponse> handleNotFoundException(
      CategoryNotFoundException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.NOT_FOUND.value(),
        "Not Found",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(CategoryDuplicatedException.class)
  public ResponseEntity<ExceptionResponse> handleDuplicateException(
      CategoryDuplicatedException exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.CONFLICT.value(),
        "Conflict",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception) {
    ExceptionResponse response = new ExceptionResponse(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Internal Server Error",
        exception.getMessage(),
        null);
    return ResponseEntity.status(response.statusCode()).body(response);
  }
}
