package com.server.src.main.java.com.server;
// no explicit boilerplate constructor needed if a record is used to store the record

public class CustomerID {
	private final String key;

	public CustomerID(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}