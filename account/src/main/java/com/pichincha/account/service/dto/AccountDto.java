package com.pichincha.account.service.dto;

import com.pichincha.account.domain.entity.enums.AccountTypeEnum;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class AccountDto {

  Long id;
  @NotEmpty
  @Size(max = 20, message = "Number cannot exceed 10 digits")
  String number;
  @NotNull
  AccountTypeEnum accountType;
  @NotNull
  @PositiveOrZero(message = "Initial balance must be equal or greater than 0")
  BigDecimal initialBalance;
  @NotNull
  Boolean status;
  Long clientId;
  @NotNull
  String clientIdentification;
  List<MovementDto> movements;


}
