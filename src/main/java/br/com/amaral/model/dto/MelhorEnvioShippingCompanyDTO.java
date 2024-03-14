package br.com.amaral.model.dto;

import java.io.Serializable;

/**
 * MelhorEnvio API
 */
public class MelhorEnvioShippingCompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String name;
	private String price;
	private String company;
	private String picture;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getPicture() {
		return picture;
	}
	
	public void setPicture(String picture) {
		this.picture = picture;
	}

	public boolean isOK() {

		if (id != null && name != null && price != null && company != null) {
			return true;
		}
		return false;
	}
}