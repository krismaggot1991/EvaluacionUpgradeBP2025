package com.pichincha.account.repository;


import com.pichincha.account.domain.entity.Account;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByNumber(String number);

  @Query(value = "SELECT DISTINCT a.* FROM accounts a" +
      " JOIN movements m on a.account_id = m.account_id" +
      " WHERE a.client_id = ?1" +
      " AND m.date BETWEEN ?2 AND ?3", nativeQuery = true)
  List<Account> findAccountsAndMovementsByClientIdAndDate(Long clientId, LocalDate initialDate, LocalDate endDate);
}
