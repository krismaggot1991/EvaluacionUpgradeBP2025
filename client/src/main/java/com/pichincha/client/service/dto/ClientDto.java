package com.pichincha.client.service.dto;

import com.pichincha.client.domain.enums.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClientDto {

  Long id;

  @NotEmpty
  @Size(max = 100, message = "Name cannot exceed 100 characters")
  String name;

  @NotNull
  Gender gender;

  @NotNull
  Integer age;

  @NotEmpty
  @Size(max = 10, message = "Identification cannot exceed 10 digits")
  String identification;

  @NotEmpty
  @Size(max = 100, message = "Address cannot exceed 45 characters")
  String address;

  @NotEmpty
  @Size(max = 10, message = "Phone cannot exceed 10 digits")
  String phone;

  @NotEmpty
  @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
  String password;

  @NotNull
  Boolean status;

}
