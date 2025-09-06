package com.pichincha.client.service.mapper;

import com.pichincha.client.domain.entity.Client;
import com.pichincha.client.service.dto.ClientDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

  ClientDto toDto(Client client);

  Client toEntity(ClientDto clientDto);
}
