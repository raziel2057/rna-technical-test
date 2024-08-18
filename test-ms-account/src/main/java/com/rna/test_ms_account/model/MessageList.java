package com.rna.test_ms_account.model;

import java.util.List;

public abstract class MessageList {
	protected List<Message> messages;

	public MessageList() {
		super();
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
}
