package com.pichincha.client.domain.entity;

import com.pichincha.client.domain.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;

@Data
@Entity(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "person_id", nullable = false, unique = true, updatable = false)
  Long id;
  @Column(length = 100, nullable = false)
  String name;
  @Enumerated(value = EnumType.STRING)
  @Column(length = 20, nullable = false)
  Gender gender;
  @Column(length = 3, nullable = false)
  Integer age;
  @Column(length = 10, nullable = false, unique = true)
  String identification;
  @Column(length = 100, nullable = false)
  String address;
  @Column(length = 10, nullable = false)
  String phone;
}
