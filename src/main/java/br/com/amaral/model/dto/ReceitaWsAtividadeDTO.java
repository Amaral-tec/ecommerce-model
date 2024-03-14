package br.com.amaral.model.dto;

import java.io.Serializable;

/**
 * RevenueWS API
 */
public class ReceitaWsAtividadeDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String code;
	private String text;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
