package com.rna.test_ms_client.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rna.test_ms_client.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
