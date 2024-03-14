package br.com.amaral.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * MelhorEnvio API
 */
public class MelhorEnvioShippingDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private MelhorEnvioShippingFromDTO from;

	private QuoteShippingToDTO to;

	private List<MelhorEnvioShippingProductsDTO> products = new ArrayList<>();

	public MelhorEnvioShippingFromDTO getFrom() {
		return from;
	}

	public void setFrom(MelhorEnvioShippingFromDTO from) {
		this.from = from;
	}

	public QuoteShippingToDTO getTo() {
		return to;
	}

	public void setTo(QuoteShippingToDTO to) {
		this.to = to;
	}

	public List<MelhorEnvioShippingProductsDTO> getProducts() {
		return products;
	}

	public void setProducts(List<MelhorEnvioShippingProductsDTO> products) {
		this.products = products;
	}

}
