package com.rna.test_ms_client.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity(name = "persona")
//@Table(name = "Persona")
@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Person implements Serializable {
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "identificacion")
    private Long identification;
 	
 	@Column(name = "nombre", unique = true, nullable = false)
    private String name;
 	
 	@Column(name = "genero")
    private String gender;
 	
 	@Column(name = "edad", nullable = false)
    private int age;
 	
 	@Column(name = "direccion")
    private String address;
 	
 	@Column(name = "telefono")
    private String phone;
 	
 	

	public Person() {
		super();
	}

	public Person(String name, String gender, int age, String address, String phone) {
		super();
		//this.identification = identification;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.address = address;
		this.phone = phone;
	}

	public Long getIdentification() {
		return identification;
	}

	public void setIdentification(Long identification) {
		this.identification = identification;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
 	
 	
}
