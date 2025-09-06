package com.pichincha.client.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.pichincha.client.service.ClientService;
import com.pichincha.client.service.dto.ClientDto;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

  @Mock
  ClientService clientService;

  @InjectMocks
  ClientController clientController;

  ClientDto clientDto;
  List<ClientDto> clientDtoList;

  @BeforeEach
  void setUp() {
    clientDto = new ClientDto();
    clientDto.setId(1L);
    clientDto.setIdentification("1234567890");

    clientDtoList = List.of(clientDto);
  }

  @Test
  void shouldSaveClient() {
    clientController.saveClient(clientDto);
    verify(clientService, times(1)).saveClient(clientDto);
  }

  @Test
  void shouldSaveClientsList() {
    clientController.saveClientsList(clientDtoList);
    verify(clientService, times(1)).saveClientList(clientDtoList);
  }

  @Test
  void shouldUpdateClient() {
    clientController.updateClient(1L, clientDto);
    verify(clientService, times(1)).updateClient(1L, clientDto);
  }

  @Test
  void shouldDeleteClient() {
    clientController.deleteClient(1L);
    verify(clientService, times(1)).deleteClient(1L);
  }

  @Test
  void shouldFindAllClients() {
    when(clientService.findAllClients()).thenReturn(clientDtoList);
    ResponseEntity<List<ClientDto>> response = clientController.findAllClients();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(clientDtoList, response.getBody());
    verify(clientService, times(1)).findAllClients();
  }

  @Test
  void shouldFindClientByIdentification() {
    when(clientService.findClientByIdentification("1234567890")).thenReturn(Optional.of(clientDto));
    ResponseEntity<Optional<ClientDto>> response = clientController.findClientByIdentification("1234567890");
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(Optional.of(clientDto), response.getBody());
    verify(clientService, times(1)).findClientByIdentification("1234567890");
  }
}
