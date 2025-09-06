package com.pichincha.client.util;

public class ClientConstants {

  public static final String CLIENT_NOT_FOUNDED = "Client with ID %s not found";
  public static final String GENERIC_ERROR_MESSAGE = "Internal Server Error";
  public static final String DATA_ERROR_MESSAGE = "Data error";

  private ClientConstants() throws IllegalAccessException {
    throw new IllegalAccessException("Constants class can not be instantiated");
  }

}
