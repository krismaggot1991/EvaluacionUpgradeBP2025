package com.pichincha.client.controller;


import static lombok.AccessLevel.PRIVATE;

import com.pichincha.client.service.ClientService;
import com.pichincha.client.service.dto.ClientDto;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/client")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ClientController {

  ClientService clientService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void saveClient(@Valid @RequestBody ClientDto clientDto) {
    clientService.saveClient(clientDto);
  }

  @PostMapping("/save-list")
  @ResponseStatus(HttpStatus.CREATED)
  public void saveClientsList(@Valid @RequestBody List<ClientDto> clientDtoList) {
    clientService.saveClientList(clientDtoList);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateClient(@PathVariable Long id, @Valid @RequestBody ClientDto clientDto) {
    clientService.updateClient(id, clientDto);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void deleteClient(@PathVariable Long id) {
    clientService.deleteClient(id);
  }

  @GetMapping
  public ResponseEntity<List<ClientDto>> findAllClients() {
    return ResponseEntity.ok(clientService.findAllClients());
  }

  @GetMapping("/{identification}")
  public ResponseEntity<Optional<ClientDto>> findClientByIdentification(@PathVariable String identification) {
    return ResponseEntity.ok(clientService.findClientByIdentification(identification));
  }
}
