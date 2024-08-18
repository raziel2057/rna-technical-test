package com.rna.test_ms_account.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rna.test_ms_account.model.Message;
import com.rna.test_ms_account.model.MessageList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountStatementDTO extends MessageList {
	
	private LocalDate movementDate;
	
	private String clientName;
	
	private Long accountNumber;
	
	private String accountType;
	
	private BigDecimal initialBalance;
	
	private Boolean accountState;
	
	private BigDecimal movementAmount;
	
	private BigDecimal availableBalance;

	public AccountStatementDTO() {
		super();
	}

	public AccountStatementDTO(LocalDate movementDate, String clientName, Long accountNumber, String accountType,
			BigDecimal initialBalance, Boolean accountState, BigDecimal movementAmount, BigDecimal availableBalance) {
		super();
		this.movementDate = movementDate;
		this.clientName = clientName;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.initialBalance = initialBalance;
		this.accountState = accountState;
		this.movementAmount = movementAmount;
		this.availableBalance = availableBalance;
	}
	
	private AccountStatementDTO(Builder builder) {
        this.messages = builder.messages;

    }

	public LocalDate getMovementDate() {
		return movementDate;
	}

	public void setMovementDate(LocalDate movementDate) {
		this.movementDate = movementDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public BigDecimal getInitialBalance() {
		return initialBalance;
	}

	public void setInitialBalance(BigDecimal initialBalance) {
		this.initialBalance = initialBalance;
	}

	public Boolean getAccountState() {
		return accountState;
	}

	public void setAccountState(Boolean accountState) {
		this.accountState = accountState;
	}

	public BigDecimal getMovementAmount() {
		return movementAmount;
	}

	public void setMovementAmount(BigDecimal movementAmount) {
		this.movementAmount = movementAmount;
	}

	public BigDecimal getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(BigDecimal availableBalance) {
		this.availableBalance = availableBalance;
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

        public AccountStatementDTO build() {
            return new AccountStatementDTO(this);
        }
    }
	
	
}
