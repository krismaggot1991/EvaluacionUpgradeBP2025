package com.pichincha.account.controller;

import static lombok.AccessLevel.PRIVATE;

import com.pichincha.account.service.MovementService;
import com.pichincha.account.service.dto.MovementDto;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/movement")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class MovementController {

  MovementService service;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void saveMovement(@Valid @RequestBody MovementDto movementDto) {
    service.saveMovement(movementDto);
  }

  @PostMapping("/save-list")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveMovementsList(@Valid @RequestBody List<MovementDto> movementDtoList) {
    service.saveMovementsList(movementDtoList);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateMovement(@PathVariable Long id, @Valid @RequestBody MovementDto movementDto) {
    service.updateMovement(id, movementDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteMovement(@PathVariable Long id) {
    service.deleteMovement(id);
  }

  @GetMapping
  public ResponseEntity<List<MovementDto>> findAllMovements() {
    return ResponseEntity.ok(service.findAllMovements());
  }
}
