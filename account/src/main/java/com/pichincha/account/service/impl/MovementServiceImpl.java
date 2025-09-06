package com.pichincha.account.service.impl;

import static com.pichincha.account.util.AccountConstants.ACCOUNT_NOT_FOUND_MESSAGE;
import static com.pichincha.account.util.AccountConstants.MOVEMENT_NOT_FOUND_MESSAGE;
import static lombok.AccessLevel.PRIVATE;

import com.pichincha.account.domain.entity.enums.MovementTypeEnum;
import com.pichincha.account.exception.AccountBadRequestException;
import com.pichincha.account.exception.AccountNotFoundException;
import com.pichincha.account.repository.MovementRepository;
import com.pichincha.account.service.AccountService;
import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.MovementDto;
import com.pichincha.account.service.mapper.MovementMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MovementServiceImpl implements MovementService {

  AccountService accountService;
  MovementMapper movementMapper;
  MovementRepository movementRepository;

  private AccountDto findAccountByNumber(String accountNumber) {
    return accountService.findAccountByNumber(accountNumber).filter(account -> account.getStatus().equals(true))
        .orElseThrow(() -> new AccountNotFoundException(String.format(ACCOUNT_NOT_FOUND_MESSAGE, accountNumber)));
  }

  private void validateMovement(MovementDto movementDto, AccountDto accountDto) {
    MovementTypeEnum movementType = movementDto.getMovementType();
    BigDecimal movementValue = movementDto.getValue();
    BigDecimal initialBalance = accountDto.getInitialBalance();

    if (movementValue.compareTo(BigDecimal.ZERO) == 0) {
      throw new AccountBadRequestException("Movement value must be up upper to 0");
    }

    if (MovementTypeEnum.WITHDRAWAL.equals(movementType)) {
      if (movementValue.compareTo(BigDecimal.ZERO) > 0) {
        throw new AccountBadRequestException("Value must be negative for a withdrawal");
      }

      if (initialBalance.compareTo(movementValue.abs()) < 0) {
        throw new AccountBadRequestException("Dont have the balance available to make this movement");
      }
    } else if (MovementTypeEnum.DEPOSIT.equals(movementType) && movementValue.compareTo(BigDecimal.ZERO) < 0) {
      throw new AccountBadRequestException("The value must be positive for a deposit");
    }
  }


  @Override
  public void saveMovement(MovementDto movementDto) {
    log.info("Save new movement for account with number: {}", movementDto.getAccountNumber());
    AccountDto accountDto = findAccountByNumber(movementDto.getAccountNumber());
    movementDto.setAccountId(accountDto.getId());
    validateMovement(movementDto, accountDto);

    BigDecimal initialBalance = accountDto.getInitialBalance();

    movementDto.setBalance(initialBalance);
    movementRepository.save(movementMapper.toEntity(movementDto));

    log.info("Update balance for account with ID: {}", movementDto.getAccountId());

    accountDto.setInitialBalance(initialBalance.add(movementDto.getValue()));
    accountService.updateAccount(accountDto.getId(), accountDto);
  }

  @Override
  public void saveMovementsList(List<MovementDto> movementDtoList) {
    log.info("Save new movements list {}", movementDtoList);
    movementDtoList.forEach(this::saveMovement);
  }

  @Override
  public void updateMovement(Long id, MovementDto movementDto) {
    log.info("Update movement for account with if: {}", movementDto.getAccountId());
    MovementDto movementDtoDB = findMovementById(id)
        .orElseThrow(() -> new AccountNotFoundException(String.format(MOVEMENT_NOT_FOUND_MESSAGE, id)));
    AccountDto accountDto = findAccountByNumber(movementDto.getAccountNumber());
    movementDto.setAccountId(accountDto.getId());
    movementDto.setId(id);
    BigDecimal initialBalance = accountDto.getInitialBalance();
    movementDto.setBalance(movementDtoDB.getBalance());
    movementRepository.save(movementMapper.toEntity(movementDto));
  }

  @Override
  public void deleteMovement(Long id) {
    log.info("Deleting movement with ID: {}", id);
    findMovementById(id)
        .orElseThrow(() -> new AccountNotFoundException(String.format(MOVEMENT_NOT_FOUND_MESSAGE, id)));
    movementRepository.deleteById(id);
  }

  @Override
  public Optional<MovementDto> findMovementById(Long id) {
    log.info("Delete movement with id: {}", id);
    return movementRepository.findById(id).map(movementMapper::toDto);
  }

  @Override
  public List<MovementDto> findAllMovements() {
    log.info("Find all movements");
    return movementRepository.findAll().stream().map(movementMapper::toDto).toList();
  }
}
