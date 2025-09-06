package com.pichincha.client.service;

import com.pichincha.client.service.dto.ClientDto;
import java.util.List;
import java.util.Optional;

public interface ClientService {

  void saveClient(ClientDto clientDto);

  void saveClientList(List<ClientDto> clientDtoList);

  void updateClient(Long id, ClientDto clientDto);

  void deleteClient(Long id);

  Optional<ClientDto> findClientById(Long id);

  Optional<ClientDto> findClientByIdentification(String identification);

  List<ClientDto> findAllClients();
}
