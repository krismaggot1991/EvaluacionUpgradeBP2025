package com.pichincha.account.service.dto;

import java.io.Serializable;
import lombok.Data;

@Data
public class ClientDto implements Serializable {

  Long id;
  String name;
}