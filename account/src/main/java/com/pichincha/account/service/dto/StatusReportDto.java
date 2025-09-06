package com.pichincha.account.service.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusReportDto {

  String name;
  List<AccountDto> accounts;

}
