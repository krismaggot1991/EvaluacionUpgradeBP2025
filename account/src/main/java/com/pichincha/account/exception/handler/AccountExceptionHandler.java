package com.pichincha.account.exception.handler;


import static com.pichincha.account.util.AccountConstants.DATA_ACCESS_EXCEPTION_MESSAGE;
import static com.pichincha.account.util.AccountConstants.FEIGN_ERROR_MESSAGE;
import static com.pichincha.account.util.AccountConstants.GENERIC_EXCEPTION_MESSAGE;

import com.pichincha.account.exception.AccountBadRequestException;
import com.pichincha.account.exception.AccountNotFoundException;
import feign.FeignException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

  private final Logger log = LoggerFactory.getLogger(AccountExceptionHandler.class);

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
      WebRequest request) {

    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {

      String fieldName = ((FieldError) error).getField();
      String message = error.getDefaultMessage();
      errors.put(fieldName, message);
    });
    return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Object> handleGenericException(Exception ex) {
    log.error(GENERIC_EXCEPTION_MESSAGE, ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GENERIC_EXCEPTION_MESSAGE);
  }

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<Object> handleFeignException(FeignException ex) {
    log.error(FEIGN_ERROR_MESSAGE, ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(FEIGN_ERROR_MESSAGE);
  }

  @ExceptionHandler(AccountNotFoundException.class)
  public ResponseEntity<Object> handleNotFoundException(AccountNotFoundException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(DataAccessException.class)
  public ResponseEntity<Object> handleDataIntegrityViolationException(DataAccessException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DATA_ACCESS_EXCEPTION_MESSAGE);
  }

  @ExceptionHandler(AccountBadRequestException.class)
  public ResponseEntity<Object> handleBadRequestException(AccountBadRequestException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }
}
