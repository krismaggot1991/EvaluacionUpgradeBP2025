package com.pichincha.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.StatusReportDto;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class AccountControllerTest {

  @Mock
  private AccountService accountService;

  @InjectMocks
  private AccountController accountController;

  private AccountDto accountDto;
  private List<AccountDto> accountDtoList;
  private StatusReportDto statusReportDto;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    accountDto = new AccountDto();
    accountDto.setId(1L);
    accountDto.setNumber("1234567890");
    accountDto.setInitialBalance(BigDecimal.valueOf(1000));
    accountDto.setStatus(true);
    accountDto.setClientId(1L);
    accountDto.setClientIdentification("1234567890");
    accountDtoList = Collections.singletonList(accountDto);
    statusReportDto = new StatusReportDto("Test Client", accountDtoList);
  }

  @Test
  void testSaveAccount() {
    doNothing().when(accountService).saveAccount(any(AccountDto.class));
    accountController.saveAccount(accountDto);
    verify(accountService, times(1)).saveAccount(any(AccountDto.class));
  }

  @Test
  void testSaveAccountsList() {
    doNothing().when(accountService).saveAccountList(anyList());
    accountController.saveAccountsList(accountDtoList);
    verify(accountService, times(1)).saveAccountList(anyList());
  }

  @Test
  void shouldUpdateAccount() {
    doNothing().when(accountService).updateAccount(anyLong(), any(AccountDto.class));
    accountController.updateAccount(1L, accountDto);
    verify(accountService, times(1)).updateAccount(anyLong(), any(AccountDto.class));
  }

  @Test
  void shouldDeleteAccount() {
    doNothing().when(accountService).deleteAccount(anyLong());
    accountController.deleteAccount(1L);
    verify(accountService, times(1)).deleteAccount(anyLong());
  }

  @Test
  void shouldFindAllAccounts() {
    when(accountService.findAllAccounts()).thenReturn(accountDtoList);
    ResponseEntity<List<AccountDto>> response = accountController.findAllAccounts();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(accountDtoList, response.getBody());
    verify(accountService, times(1)).findAllAccounts();
  }

  @Test
  void shouldGenerateBankStatementReport() {
    when(accountService.generateBankStatementReport(any(LocalDate.class), any(LocalDate.class), anyString())).thenReturn(statusReportDto);
    ResponseEntity<StatusReportDto> response = accountController.generateBankStatementReport(LocalDate.now(), LocalDate.now(), "1234567890");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(statusReportDto, response.getBody());
    verify(accountService, times(1)).generateBankStatementReport(any(LocalDate.class), any(LocalDate.class), anyString());
  }
}
