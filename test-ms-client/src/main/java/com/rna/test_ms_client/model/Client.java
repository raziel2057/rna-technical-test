package com.rna.test_ms_client.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "cliente")
public class Client extends Person implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
    private Long clientId;
	
	@Column(name = "contrasena", nullable = false)
    private String password;

    @Column(name = "estado", nullable = false)
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
    
}
