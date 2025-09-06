package com.pichincha.account.service.mapper;

import com.pichincha.account.domain.entity.Account;
import com.pichincha.account.service.dto.AccountDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

  AccountDto toDto(Account account);

  Account toEntity(AccountDto accountDto);
}
