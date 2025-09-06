package com.pichincha.account.service;

import com.pichincha.account.service.dto.MovementDto;
import java.util.List;
import java.util.Optional;

public interface MovementService {

  void saveMovement(MovementDto movementDto);

  void saveMovementsList(List<MovementDto> movementDtoList);

  void updateMovement(Long id, MovementDto movementDto);

  void deleteMovement(Long id);

  Optional<MovementDto> findMovementById(Long id);

  List<MovementDto> findAllMovements();
}
