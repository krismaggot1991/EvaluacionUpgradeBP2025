package com.pichincha.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.account.domain.entity.Account;
import com.pichincha.account.domain.entity.Movement;
import com.pichincha.account.domain.entity.enums.MovementTypeEnum;
import com.pichincha.account.exception.AccountBadRequestException;
import com.pichincha.account.exception.AccountNotFoundException;
import com.pichincha.account.repository.MovementRepository;
import com.pichincha.account.service.dto.AccountDto;
import com.pichincha.account.service.dto.MovementDto;
import com.pichincha.account.service.impl.MovementServiceImpl;
import com.pichincha.account.service.mapper.MovementMapper;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MovementServiceTest {

  @Mock
  private AccountService accountService;

  @Mock
  private MovementMapper movementMapper;

  @Mock
  private MovementRepository movementRepository;

  @InjectMocks
  private MovementServiceImpl movementService;

  private AccountDto accountDto;
  private MovementDto movementDto;
  private Movement movement;
  private Account account;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    account = new Account();
    account.setId(1L);
    account.setNumber("1234567890");

    accountDto = new AccountDto();
    accountDto.setId(1L);
    accountDto.setNumber("1234567890");
    accountDto.setInitialBalance(BigDecimal.valueOf(1000));
    accountDto.setStatus(true);

    movementDto = new MovementDto();
    movementDto.setId(1L);
    movementDto.setAccountNumber("1234567890");
    movementDto.setMovementType(MovementTypeEnum.DEPOSIT);
    movementDto.setValue(BigDecimal.valueOf(500));

    movement = new Movement();
    movement.setId(1L);
    movement.setAccount(account);
    movement.setValue(BigDecimal.valueOf(500));
  }

  @Test
  void shouldSaveMovement() {
    when(accountService.findAccountByNumber(anyString())).thenReturn(Optional.of(accountDto));
    when(movementMapper.toEntity(any(MovementDto.class))).thenReturn(movement);
    when(movementRepository.save(any(Movement.class))).thenReturn(movement);
    doNothing().when(accountService).updateAccount(anyLong(), any(AccountDto.class));

    movementService.saveMovement(movementDto);

    verify(accountService).findAccountByNumber(anyString());
    verify(movementMapper).toEntity(any(MovementDto.class));
    verify(movementRepository).save(any(Movement.class));
    verify(accountService).updateAccount(anyLong(), any(AccountDto.class));
  }

  @Test
  void shouldSaveMovementsList() {
    when(accountService.findAccountByNumber(anyString())).thenReturn(Optional.of(accountDto));
    when(movementMapper.toEntity(any(MovementDto.class))).thenReturn(movement);
    when(movementRepository.save(any(Movement.class))).thenReturn(movement);
    doNothing().when(accountService).updateAccount(anyLong(), any(AccountDto.class));

    List<MovementDto> movementsList = Collections.singletonList(movementDto);
    movementService.saveMovementsList(movementsList);

    verify(accountService, times(movementsList.size())).findAccountByNumber(anyString());
    verify(movementMapper, times(movementsList.size())).toEntity(any(MovementDto.class));
    verify(movementRepository, times(movementsList.size())).save(any(Movement.class));
  }

  @Test
  void shouldUpdateMovement() {
    when(movementRepository.findById(anyLong())).thenReturn(Optional.of(movement));
    when(accountService.findAccountByNumber(anyString())).thenReturn(Optional.of(accountDto));
    when(movementMapper.toDto(any(Movement.class))).thenReturn(movementDto);
    when(movementRepository.save(any(Movement.class))).thenReturn(movement);

    movementService.updateMovement(1L, movementDto);

    verify(movementRepository).findById(anyLong());
    verify(accountService).findAccountByNumber(anyString());
    verify(movementMapper).toEntity(any(MovementDto.class));
  }

  @Test
  void shouldDeleteMovement() {
    when(movementRepository.findById(anyLong())).thenReturn(Optional.of(movement));
    when(movementMapper.toDto(any(Movement.class))).thenReturn(movementDto);
    doNothing().when(movementRepository).deleteById(anyLong());

    movementService.deleteMovement(1L);

    verify(movementRepository).findById(anyLong());
    verify(movementRepository).deleteById(anyLong());
  }

  @Test
  void shouldFindMovementById() {
    when(movementRepository.findById(anyLong())).thenReturn(Optional.of(movement));
    when(movementMapper.toDto(any(Movement.class))).thenReturn(movementDto);

    Optional<MovementDto> result = movementService.findMovementById(1L);

    assertTrue(result.isPresent());
    assertEquals(movementDto, result.get());
    verify(movementRepository).findById(anyLong());
    verify(movementMapper).toDto(any(Movement.class));
  }

  @Test
  void shouldFindAllMovements() {
    when(movementRepository.findAll()).thenReturn(Collections.singletonList(movement));
    when(movementMapper.toDto(any(Movement.class))).thenReturn(movementDto);

    List<MovementDto> result = movementService.findAllMovements();

    assertEquals(1, result.size());
    assertEquals(movementDto, result.get(0));
    verify(movementRepository).findAll();
    verify(movementMapper).toDto(any(Movement.class));
  }

  @Test
  void shouldValidateMovementWithZeroValue() {
    movementDto.setValue(BigDecimal.ZERO);
    assertThrows(AccountNotFoundException.class, () -> movementService.saveMovement(movementDto));
  }

  @Test
  void shouldValidateMovementWithNegativeValueForDeposit() {
    movementDto.setMovementType(MovementTypeEnum.DEPOSIT);
    movementDto.setValue(BigDecimal.valueOf(-500));
    assertThrows(AccountNotFoundException.class, () -> movementService.saveMovement(movementDto));
  }

  @Test
  void shouldValidateMovementWithPositiveValueForWithdrawal() {
    movementDto.setMovementType(MovementTypeEnum.WITHDRAWAL);
    movementDto.setValue(BigDecimal.valueOf(500));
    assertThrows(AccountNotFoundException.class, () -> movementService.saveMovement(movementDto));
  }

  @Test
  void shouldValidateMovementWithInsufficientBalanceForWithdrawal() {
    accountDto.setInitialBalance(BigDecimal.valueOf(100));
    movementDto.setMovementType(MovementTypeEnum.WITHDRAWAL);
    movementDto.setValue(BigDecimal.valueOf(-200));
    when(accountService.findAccountByNumber(anyString())).thenReturn(Optional.of(accountDto));
    assertThrows(AccountBadRequestException.class, () -> movementService.saveMovement(movementDto));
  }
}
