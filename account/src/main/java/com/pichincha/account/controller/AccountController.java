package com.pichincha.account.controller;

import static lombok.AccessLevel.PRIVATE;

import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.StatusReportDto;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class AccountController {

  AccountService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void saveAccount(@Valid @RequestBody AccountDto accountDto) {
    service.saveAccount(accountDto);
  }

  @PostMapping("/save-list")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveAccountsList(@Valid @RequestBody List<AccountDto> accountDtoList) {
    service.saveAccountList(accountDtoList);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateAccount(@PathVariable Long id, @Valid @RequestBody AccountDto accountDto) {
    service.updateAccount(id, accountDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteAccount(@PathVariable Long id) {
    service.deleteAccount(id);
  }

  @GetMapping
  public ResponseEntity<List<AccountDto>> findAllAccounts() {
    return ResponseEntity.ok(service.findAllAccounts());
  }

  @GetMapping("/report")
  public ResponseEntity<StatusReportDto> generateBankStatementReport(
      @RequestParam(name = "initialDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate initialDate,
      @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
      @RequestParam String clientIdentification) {
    return ResponseEntity.ok(service.generateBankStatementReport(initialDate, endDate, clientIdentification));
  }
}
