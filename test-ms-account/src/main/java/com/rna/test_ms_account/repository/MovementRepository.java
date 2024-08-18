package com.rna.test_ms_account.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rna.test_ms_account.DTO.AccountStatementDTO;
import com.rna.test_ms_account.model.Movement;

public interface MovementRepository extends JpaRepository<Movement, Long> {
	@Query("SELECT m FROM movement m WHERE m.account.accountNumber = :accountNumber")
	 List<Movement> findMovementsByAccountNumber(@Param("accountNumber") Long accountNumber);
	
	@Query("SELECT m.balance FROM movement m WHERE m.account.accountNumber = :accountNumber ORDER BY m.id DESC LIMIT 1")
	 BigDecimal findLastBalanceByAccountNumber(@Param("accountNumber") Long accountNumber);
	
	@Query("SELECT new com.rna.test_ms_account.DTO.AccountStatementDTO( m.movementDate, m.account.person.name, m.account.accountNumber, "
			+ "m.account.accountType, m.account.initialBalance, m.account.state, "
			+ "CASE "
			+ "    WHEN m.movementType = 'DEBIT' THEN m.amount * -1 "
			+ "    ELSE m.amount "
			+ "END, "
			+ "m.balance) "
			+ "FROM movement m "
			+ "WHERE m.account.person.name = :clientName "
			+ "AND m.movementDate BETWEEN :initialDate AND :finishDate")
	 List<AccountStatementDTO> getAccountStatement(@Param("clientName") String clientName, 
			                                       @Param("initialDate") LocalDate initialDate, 
			                                       @Param("finishDate") LocalDate finishDate);
}
