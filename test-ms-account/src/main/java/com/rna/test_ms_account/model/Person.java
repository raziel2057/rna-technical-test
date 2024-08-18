package com.rna.test_ms_account.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity(name = "person")
@Table(name = "person")
@Inheritance(strategy = InheritanceType.JOINED)
public class Person implements Serializable {
 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "identification")
    private Long identification;
 	
 	@Column(name = "name", unique = true, nullable = false)
    private String name;
 	
 	@Column(name = "gender")
    private String gender;
 	
 	@Column(name = "age")
    private int age;
 	
 	@Column(name = "address")
    private String address;
 	
 	@Column(name = "phone")
    private String phone;
 	
	public Person() {
		super();
	}

	private Person(Builder builder) {
        this.identification = builder.identification;
        /*this.name = builder.name;
        this.gender = builder.gender;
        this.age = builder.age;
        this.address = builder.address;
        this.phone = builder.phone;*/
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
	
	public static class Builder {
        private Long identification;
        /*private String name;
        private String gender;
        private Integer age;
        private String address;
        private String phone;*/

        public Builder setIdentification(Long identification) {
            this.identification = identification;
            return this;
        }

        /*public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setAge(Integer age) {
            this.age = age;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }*/

        public Person build() {
            return new Person(this);
        }
    }
 	
}
