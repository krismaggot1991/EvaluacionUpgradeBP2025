package com.pichincha.account.service;

import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.StatusReportDto;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccountService {

  void saveAccount(AccountDto accountDto);

  void saveAccountList(List<AccountDto> accountDtoList);

  void updateAccount(Long id, AccountDto accountDto);

  void deleteAccount(Long id);

  Optional<AccountDto> findAccountById(Long id);

  Optional<AccountDto> findAccountByNumber(String number);

  List<AccountDto> findAllAccounts();

  StatusReportDto generateBankStatementReport(LocalDate initialDate, LocalDate endDate, String clientIdentification);
}
