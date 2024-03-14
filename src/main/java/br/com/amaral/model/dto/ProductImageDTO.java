package br.com.amaral.model.dto;

import java.io.Serializable;

public class ProductImageDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String originalImage;
	private String thumbnailImage;
	private Long product;
	private Long legalEntity;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getOriginalImage() {
		return originalImage;
	}
	
	public void setOriginalImage(String originalImage) {
		this.originalImage = originalImage;
	}
	
	public String getThumbnailImage() {
		return thumbnailImage;
	}
	
	public void setThumbnailImage(String thumbnailImage) {
		this.thumbnailImage = thumbnailImage;
	}
	
	public Long getProduct() {
		return product;
	}
	
	public void setProduct(Long product) {
		this.product = product;
	}
	
	public Long getLegalEntity() {
		return legalEntity;
	}
	
	public void setLegalEntity(Long legalEntity) {
		this.legalEntity = legalEntity;
	}

}
