package br.com.amaral.model.dto;

import java.io.Serializable;

/**
 * MelhorEnvio API
 */
public class MelhorEnvioTrackingDataInvoiceDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
