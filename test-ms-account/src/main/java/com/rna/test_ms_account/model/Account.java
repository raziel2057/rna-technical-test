package com.rna.test_ms_account.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "account")
@Table(name = "account")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Account extends MessageList {
	
	@Id
    @Column(name = "account_number", nullable = false)
    private Long accountNumber;

    @Column(name = "account_type", nullable = false)
    private String accountType;

    @Column(name = "initial_balance", nullable = false, precision = 15, scale = 2)
    private BigDecimal initialBalance;

    @Column(name = "state", nullable = false)
    private Boolean state;

    @ManyToOne
    @JoinColumn(name = "identification", nullable = false)
    private Person person;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clientName;
    
    public Account() {
		super();
	}

	public Account(Long accountNumber, String accountType, BigDecimal initialBalance, Boolean state, Long identification) {
    	this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.initialBalance = initialBalance;
		this.state = state;
		this.person = new Person.Builder().setIdentification(identification).build();
	}
	
	private Account(Builder builder) {
        this.messages = builder.messages;

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

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Person getPersona() {
        return person;
    }

    public void setPersona(Person person) {
        this.person = person;
    }

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

        public Account build() {
            return new Account(this);
        }
    }

}
