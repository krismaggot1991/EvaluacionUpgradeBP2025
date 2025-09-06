package com.pichincha.client.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "clients")
@PrimaryKeyJoinColumn(referencedColumnName = "person_id", name = "client_id")
public class Client extends Person {


  @Column(length = 4, nullable = false)
  Integer password;
  @Column(nullable = false)
  Boolean status;
}