package com.pichincha.account.util;

public class AccountConstants {

  public static final String CLIENT_NOT_FOUND_MESSAGE = "Client with identification %s not found";
  public static final String GENERIC_EXCEPTION_MESSAGE = "Has been occurred unexpected system error";
  public static final String FEIGN_ERROR_MESSAGE = "Has been occurred an error when consume feign client";
  public static final String DATA_ACCESS_EXCEPTION_MESSAGE = "Has been occurred data error";
  public static final String ACCOUNT_NOT_FOUND_MESSAGE = "Account with id %s not found";
  public static final String MOVEMENT_NOT_FOUND_MESSAGE = "Movement with id %s not found";

  private AccountConstants() throws IllegalAccessException {
    throw new IllegalAccessException("Constants class can not be instantiated");
  }

}
