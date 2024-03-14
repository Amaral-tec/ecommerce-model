package br.com.amaral.model.dto;

import br.com.amaral.model.Product;

public class ProductSoldDTO {

	private Product product;

	private Double quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}
	
}
