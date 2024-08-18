package com.rna.test_ms_account.model;

public class Message {
	
	private int code; 
	private String message;

	public Message() {

	}
	
	public Message(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
