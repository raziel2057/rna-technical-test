package com.rna.test_ms_client.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity(name = "client")
@Table(name = "client")
public class Client extends Person implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "client_id")
    private Long clientId;
	
	@Column(name = "password", nullable = false)
    private String password;

    @Column(name = "state", nullable = false)
    private Boolean state;
    
	public Client() {
		super();
	}

	public Client(String name, String gender, int age, String address, String phone,
			String password, boolean state) {
		super(name, gender, age, address, phone);
		UUID uuid = UUID.randomUUID();
		this.clientId = uuid.getMostSignificantBits();
		this.password = password;
		this.state = state;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Client [clientId=" + clientId + ", password=" + password + ", state=" + state + "]";
	}
    
}
