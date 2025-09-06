package com.pichincha.account.domain.entity;

import com.pichincha.account.domain.entity.enums.MovementTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity(name = "movements")
public class Movement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "movement_id", nullable = false, unique = true, updatable = false)
  Long id;

  @Column(nullable = false)
  LocalDate date;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "movement_type", length = 10, nullable = false)
  MovementTypeEnum movementType;

  @Column(nullable = false)
  BigDecimal value;

  @Column(nullable = false)
  BigDecimal balance;

  @ManyToOne
  @JoinColumn(name = "account_id", nullable = false)
  Account account;

}