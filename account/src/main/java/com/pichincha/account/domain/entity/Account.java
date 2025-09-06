package com.pichincha.account.domain.entity;

import com.pichincha.account.domain.entity.enums.AccountTypeEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Data
@Entity(name = "accounts")
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id", nullable = false, unique = true, updatable = false)
  Long id;

  @Column(name = "number", length = 20, unique = true, nullable = false)
  String number;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "account_type", length = 10, nullable = false)
  AccountTypeEnum accountType;

  @Column(name = "initial_balance", nullable = false)
  BigDecimal initialBalance;

  @Column(nullable = false)
  Boolean status;

  @Column(name = "client_id", nullable = false, updatable = false)
  Long clientId;

  @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
  List<Movement> movements;

  public void setInitialBalance(BigDecimal initialBalance) {
    if (initialBalance.compareTo(BigDecimal.ZERO) < 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Initial balance cannot be less than 0");
    }
    this.initialBalance = initialBalance;
  }
}
