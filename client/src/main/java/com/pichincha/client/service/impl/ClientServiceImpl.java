package com.pichincha.client.service.impl;

import static com.pichincha.client.util.ClientConstants.CLIENT_NOT_FOUNDED;
import static lombok.AccessLevel.PRIVATE;

import com.pichincha.client.domain.entity.Client;
import com.pichincha.client.exception.NotFoundException;
import com.pichincha.client.repository.ClientRepository;
import com.pichincha.client.service.ClientService;
import com.pichincha.client.service.dto.ClientDto;
import com.pichincha.client.service.mapper.ClientMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {

  ClientRepository clientRepository;
  ClientMapper clientMapper;

  @Override
  public void saveClient(ClientDto clientDto) {
    log.info("Add new client with identification: {}", clientDto.getIdentification());
    clientRepository.save(clientMapper.toEntity(clientDto));
  }

  @Override
  public void saveClientList(List<ClientDto> clientDtoList) {
    log.info("Add list of clients: {}", clientDtoList);
    List<Client> clients = clientDtoList.stream().map(clientMapper::toEntity).toList();
    clientRepository.saveAll(clients);
  }

  @Override
  public void updateClient(Long id, ClientDto clientDto) {
    log.info("Update client with id: {}", id);
    findClientById(id)
        .orElseThrow(() -> new NotFoundException(String.format(CLIENT_NOT_FOUNDED, id)));
    clientDto.setId(id);
    clientRepository.save(clientMapper.toEntity(clientDto));
  }

  @Override
  public void deleteClient(Long id) {
    log.info("Delete client with id: {}", id);
    findClientById(id)
        .orElseThrow(() -> new NotFoundException(String.format(CLIENT_NOT_FOUNDED, id)));
    clientRepository.deleteById(id);
  }

  @Override
  public Optional<ClientDto> findClientById(Long id) {
    log.info("Find client with id: {}", id);
    return clientRepository.findById(id).map(clientMapper::toDto);
  }

  @Override
  public Optional<ClientDto> findClientByIdentification(String identification) {
    log.info("Find client with identification: {}", identification);
    return clientRepository.findByIdentification(identification).map(clientMapper::toDto);
  }

  @Override
  public List<ClientDto> findAllClients() {
    log.info("Find all clients");
    return clientRepository.findAll().stream().map(clientMapper::toDto).toList();
  }
}
