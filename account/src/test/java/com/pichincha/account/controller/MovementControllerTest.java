package com.pichincha.account.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.account.domain.entity.enums.MovementTypeEnum;
import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.MovementDto;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class MovementControllerTest {

  @Mock
  private MovementService movementService;

  @InjectMocks
  private MovementController movementController;

  private MovementDto movementDto;
  private List<MovementDto> movementDtoList;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    movementDto = new MovementDto();
    movementDto.setId(1L);
    movementDto.setAccountNumber("1234567890");
    movementDto.setMovementType(MovementTypeEnum.DEPOSIT);
    movementDto.setValue(BigDecimal.valueOf(1000));
    movementDto.setBalance(BigDecimal.valueOf(1000));
    movementDto.setAccountId(1L);
    movementDtoList = Collections.singletonList(movementDto);
  }

  @Test
  void shouldSaveMovement() {
    doNothing().when(movementService).saveMovement(any(MovementDto.class));
    movementController.saveMovement(movementDto);
    verify(movementService, times(1)).saveMovement(any(MovementDto.class));
  }

  @Test
  void shouldSaveMovementsList() {
    doNothing().when(movementService).saveMovementsList(anyList());
    movementController.saveMovementsList(movementDtoList);
    verify(movementService, times(1)).saveMovementsList(anyList());
  }

  @Test
  void shouldUpdateMovement() {
    doNothing().when(movementService).updateMovement(anyLong(), any(MovementDto.class));
    movementController.updateMovement(1L, movementDto);
    verify(movementService, times(1)).updateMovement(anyLong(), any(MovementDto.class));
  }

  @Test
  void shouldDeleteMovement() {
    doNothing().when(movementService).deleteMovement(anyLong());
    movementController.deleteMovement(1L);
    verify(movementService, times(1)).deleteMovement(anyLong());
  }

  @Test
  void shouldFindAllMovements() {
    when(movementService.findAllMovements()).thenReturn(movementDtoList);
    ResponseEntity<List<MovementDto>> response = movementController.findAllMovements();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(movementDtoList, response.getBody());
    verify(movementService, times(1)).findAllMovements();
  }
}
