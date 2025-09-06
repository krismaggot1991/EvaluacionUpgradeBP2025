package com.pichincha.client.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.client.domain.entity.Client;
import com.pichincha.client.exception.NotFoundException;
import com.pichincha.client.repository.ClientRepository;
import com.pichincha.client.service.dto.ClientDto;
import com.pichincha.client.service.impl.ClientServiceImpl;
import com.pichincha.client.service.mapper.ClientMapper;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

  @Mock
  ClientRepository clientRepository;

  @Mock
  ClientMapper clientMapper;

  @InjectMocks
  ClientServiceImpl clientService;

  ClientDto clientDto;
  Client client;

  @BeforeEach
  void setUp() {
    clientDto = new ClientDto();
    clientDto.setId(1L);
    clientDto.setIdentification("1234567890");

    client = new Client();
    client.setId(1L);
    client.setIdentification("1234567890");
  }

  @Test
  void shouldSaveClient() {
    when(clientMapper.toEntity(clientDto)).thenReturn(client);
    when(clientRepository.save(client)).thenReturn(client);
    clientService.saveClient(clientDto);
    verify(clientRepository, times(1)).save(client);
  }

  @Test
  void shouldSaveClientList() {
    List<ClientDto> clientDtoList = List.of(clientDto);
    List<Client> clientList = List.of(client);
    when(clientMapper.toEntity(clientDto)).thenReturn(client);
    when(clientRepository.saveAll(clientList)).thenReturn(clientList);
    clientService.saveClientList(clientDtoList);
    verify(clientRepository, times(1)).saveAll(clientList);
  }

  @Test
  void shouldUpdateClient() {
    when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.of(client));
    when(clientMapper.toEntity(clientDto)).thenReturn(client);
    when(clientMapper.toDto(client)).thenReturn(clientDto);
    when(clientRepository.save(any(Client.class))).thenReturn(client);
    clientService.updateClient(clientDto.getId(), clientDto);
    verify(clientRepository, times(1)).save(client);
    verify(clientRepository, times(1)).findById(clientDto.getId());
  }

  @Test
  void shouldUpdateClient_NotFound() {
    when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> clientService.updateClient(clientDto.getId(), clientDto));
  }

  @Test
  void shouldDeleteClient() {
    when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.of(client));
    when(clientMapper.toDto(client)).thenReturn(clientDto);
    doNothing().when(clientRepository).deleteById(any());
    clientService.deleteClient(clientDto.getId());
    verify(clientRepository, times(1)).findById(clientDto.getId());
    verify(clientRepository, times(1)).deleteById(clientDto.getId());
  }


  @Test
  void shouldDeleteClient_NotFound() {
    when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> clientService.deleteClient(clientDto.getId()));
  }

  @Test
  void shouldFindClientById() {
    when(clientRepository.findById(clientDto.getId())).thenReturn(Optional.of(client));
    when(clientMapper.toDto(client)).thenReturn(clientDto);
    Optional<ClientDto> result = clientService.findClientById(clientDto.getId());
    assertTrue(result.isPresent());
    assertEquals(clientDto, result.get());
  }

  @Test
  void shouldFindClientByIdentification() {
    when(clientRepository.findByIdentification(clientDto.getIdentification())).thenReturn(Optional.of(client));
    when(clientMapper.toDto(client)).thenReturn(clientDto);
    Optional<ClientDto> result = clientService.findClientByIdentification(clientDto.getIdentification());
    assertTrue(result.isPresent());
    assertEquals(clientDto, result.get());
  }

  @Test
  void shouldFindAllClients() {
    List<Client> clientList = List.of(client);
    List<ClientDto> clientDtoList = List.of(clientDto);
    when(clientRepository.findAll()).thenReturn(clientList);
    when(clientMapper.toDto(client)).thenReturn(clientDto);
    List<ClientDto> result = clientService.findAllClients();
    assertEquals(clientDtoList.size(), result.size());
    assertEquals(clientDtoList.get(0), result.get(0));
  }
}
