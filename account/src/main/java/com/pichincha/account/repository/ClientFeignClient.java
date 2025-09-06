package com.pichincha.account.repository;


import com.pichincha.account.service.dto.ClientDto;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client")
public interface ClientFeignClient {

  @GetMapping("/api/client/{identification}")
  ResponseEntity<Optional<ClientDto>> findClientByIdentification(@PathVariable String identification);
}
