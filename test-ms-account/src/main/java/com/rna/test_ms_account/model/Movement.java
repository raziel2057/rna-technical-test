package com.rna.test_ms_account.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "movement")
@Table(name = "movement")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movement extends MessageList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "movement_date", nullable = false)
	private LocalDate movementDate;
	
	@Column(name = "movement_type", nullable = false)
	private String movementType;
	
	@Column(name = "amount", nullable = false, precision = 15, scale = 2)
	private BigDecimal amount;
	
	@Column(name = "balance", nullable = false, precision = 15, scale = 2)
	private BigDecimal balance;
	
	@Column(name = "state", nullable = false)
	private String state;
	
	@ManyToOne
	@JoinColumn(name = "account_number")
	private Account account;
	
	public Movement() {
		super();
	}

	public Movement(LocalDate movementDate, String movementType, BigDecimal amount, BigDecimal balance, String state,
			Long accountNumber) {
		super();
		this.movementDate = movementDate;
		this.movementType = movementType;
		this.amount = amount;
		this.balance = balance;
		this.state = state;
		Account account = new Account();
		account.setAccountNumber(accountNumber);
		this.account = account;
	}
	
	private Movement(Builder builder) {
        this.messages = builder.messages;

    }

	public Long getId() {
	    return id;
	}
	
	public void setId(Long id) {
	    this.id = id;
	}
	
	public LocalDate getMovementDate() {
	    return movementDate;
	}
	
	public void setMovementDate(LocalDate movementDate) {
	    this.movementDate = movementDate;
	}
	
	public String getMovementType() {
	    return movementType;
	}
	
	public void setMovementType(String movementType) {
	    this.movementType = movementType;
	}
	
	public BigDecimal getAmount() {
	    return amount;
	}
	
	public void setAmount(BigDecimal amount) {
	    this.amount = amount;
	}
	
	public BigDecimal getBalance() {
	    return balance;
	}
	
	public void setBalance(BigDecimal balance) {
	    this.balance = balance;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Account getAccount() {
	    return account;
	}
	
	public void setAccount(Account account) {
	    this.account = account;
	}
	
	public static class Builder {
        private List<Message> messages;

        public Builder setMessage(Message message) {
        	if(Objects.isNull(this.messages)) {
        		 this.messages = new ArrayList<Message>();
        	}
        	this.messages.add(message);
            return this;
        }

        public Movement build() {
            return new Movement(this);
        }
    }
}
