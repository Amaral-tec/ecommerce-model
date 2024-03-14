package br.com.amaral.model.dto;

import java.io.Serializable;

public class JunoBillingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String document;
	private String email;
	private String phone;
	private boolean notify = true;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDocument() {
		return document;
	}
	
	public void setDocument(String document) {
		this.document = document;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean isNotify() {
		return notify;
	}
	
	public void setNotify(boolean notify) {
		this.notify = notify;
	}
	
}
