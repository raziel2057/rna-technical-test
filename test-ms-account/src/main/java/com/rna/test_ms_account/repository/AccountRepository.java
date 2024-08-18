package com.rna.test_ms_account.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rna.test_ms_account.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	 @Query("SELECT a FROM account a WHERE a.person.identification = :identification")
	 List<Account> findAccountsByIdentification(@Param("identification") Long identification);
}
