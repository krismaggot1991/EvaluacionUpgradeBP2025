package com.pichincha.account.service.dto;

import com.pichincha.account.domain.entity.enums.MovementTypeEnum;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class MovementDto {

    Long id;
    @NotNull
    LocalDate date;
    @NotNull
    MovementTypeEnum movementType;
    @NotNull
    BigDecimal value;
    BigDecimal balance;
    Long accountId;
    @NotNull
    String accountNumber;
}
