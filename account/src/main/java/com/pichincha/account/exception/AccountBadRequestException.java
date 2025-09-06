package com.pichincha.account.exception;

public class AccountBadRequestException extends RuntimeException {

  public AccountBadRequestException(String message) {
    super(message);
  }
}
