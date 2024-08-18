package com.rna.test_ms_client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rna.test_ms_client.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	@Query(value = "SELECT p.*, c.client_id, c.password, c.state FROM person p, client c WHERE p.identification = c.identification and p.name = :name", nativeQuery = true)
	List<Client> findByClientName(String name);
}