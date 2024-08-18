package com.rna.test_ms_account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rna.test_ms_account.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("SELECT p.identification FROM person p WHERE p.name = :name")
    Long findIdentificationByBame(@Param("name") String name);
}
