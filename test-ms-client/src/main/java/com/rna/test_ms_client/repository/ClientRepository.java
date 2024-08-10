package com.rna.test_ms_client.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rna.test_ms_client.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
	@Query(value = "SELECT p.*, c.cliente_id, c.contrasena, c.estado FROM persona p, cliente c WHERE p.identificacion = c.identificacion and p.nombre = :name", nativeQuery = true)
	List<Client> findByClientName(String name);
}