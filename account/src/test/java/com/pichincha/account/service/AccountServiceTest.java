package com.pichincha.account.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.account.domain.entity.Account;
import com.pichincha.account.domain.entity.enums.AccountTypeEnum;
import com.pichincha.account.exception.AccountNotFoundException;
import com.pichincha.account.repository.AccountRepository;
import com.pichincha.account.repository.ClientFeignClient;
import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.ClientDto;
import com.pichincha.account.service.impl.AccountServiceImpl;
import com.pichincha.account.service.mapper.AccountMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class AccountServiceTest {

  @Mock
  private ClientFeignClient clientFeignClient;

  @Mock
  private AccountMapper accountMapper;

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  private AccountDto accountDto;
  private ClientDto clientDto;
  private Account account;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    clientDto = new ClientDto();
    clientDto.setId(1L);
    clientDto.setName("Test Client");

    accountDto = new AccountDto();
    accountDto.setId(1L);
    accountDto.setNumber("1234567890");
    accountDto.setAccountType(AccountTypeEnum.SAVINGS);
    accountDto.setInitialBalance(BigDecimal.valueOf(1000));
    accountDto.setStatus(true);
    accountDto.setClientId(1L);
    accountDto.setClientIdentification("1234567890");
    accountDto.setMovements(Collections.emptyList());

    account = new Account();
    account.setId(1L);
    account.setNumber("1234567890");
  }

  @Test
  void shouldFindAccountById() {
    when(accountRepository.findById(anyLong())).thenReturn(Optional.of(account));
    when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

    Optional<AccountDto> result = accountService.findAccountById(1L);

    assertEquals(accountDto, result.orElse(null));
    verify(accountRepository).findById(anyLong());
    verify(accountMapper).toDto(any(Account.class));
  }

  @Test
  void shouldFindAccountByNumber() {
    when(accountRepository.findByNumber(anyString())).thenReturn(Optional.of(account));
    when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

    Optional<AccountDto> result = accountService.findAccountByNumber("1234567890");

    assertEquals(accountDto, result.orElse(null));
    verify(accountRepository).findByNumber(anyString());
    verify(accountMapper).toDto(any(Account.class));
  }

  @Test
  void shouldFindAllAccounts() {
    when(accountRepository.findAll()).thenReturn(List.of(account));
    when(accountMapper.toDto(any(Account.class))).thenReturn(accountDto);

    List<AccountDto> result = accountService.findAllAccounts();

    assertEquals(1, result.size());
    verify(accountRepository).findAll();
    verify(accountMapper).toDto(any(Account.class));
  }

  @Test
  void shouldUpdateAccountThrowsExceptionWhenAccountNotFound() {
    when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(AccountNotFoundException.class, () -> accountService.updateAccount(1L, accountDto));
    verify(accountRepository).findById(anyLong());
  }
}
