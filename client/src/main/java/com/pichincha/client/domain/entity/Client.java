package com.pichincha.client.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "clients")
@PrimaryKeyJoinColumn(referencedColumnName = "person_id", name = "client_id")
public class Client extends Person {

  @Size(min = 6, max = 20, message = "La contraseña debe tener entre 6 y 20 caracteres")
  @Column(length = 20, nullable = false)
  String password;
  @Column(nullable = false)
  Boolean status;
}