package com.fintrack.api.exception;

public class TransactionNotFountException extends RuntimeException {

  public TransactionNotFountException(String message) {
    super(message);
  }
}
