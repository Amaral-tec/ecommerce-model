package br.com.amaral.model.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class ProductPurchaseInvoiceReportDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String productName = "";

	@NotEmpty
	private String startDate;

	@NotEmpty
	private String endDate;
	private String purchaseInvoideId = "";
	private String productId = "";
	private String purchasePrice = "";
	private String sellingPrice = "";
	private String quantityPurchased= "";
	private String supplierId = "";
	private String supplierName = "";
	private String invoiceDate = "";
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getPurchaseInvoideId() {
		return purchaseInvoideId;
	}
	
	public void setPurchaseInvoideId(String purchaseInvoideId) {
		this.purchaseInvoideId = purchaseInvoideId;
	}
	
	public String getProductId() {
		return productId;
	}
	
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getPurchasePrice() {
		return purchasePrice;
	}
	
	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
	
	public String getSellingPrice() {
		return sellingPrice;
	}
	
	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	
	public String getQuantityPurchased() {
		return quantityPurchased;
	}
	
	public void setQuantityPurchased(String quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}
	
	public String getSupplierId() {
		return supplierId;
	}
	
	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}
	
	public String getSupplierName() {
		return supplierName;
	}
	
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	public String getInvoiceDate() {
		return invoiceDate;
	}
	
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

}
