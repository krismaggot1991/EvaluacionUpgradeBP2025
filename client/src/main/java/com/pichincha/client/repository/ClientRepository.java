package com.pichincha.client.repository;

import com.pichincha.client.domain.entity.Client;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByIdentification(String identification);
}
