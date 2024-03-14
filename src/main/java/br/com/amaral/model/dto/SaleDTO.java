package br.com.amaral.model.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.amaral.model.DiscountCoupon;
import br.com.amaral.model.Individual;
import br.com.amaral.model.LegalEntity;
import br.com.amaral.model.PaymentMethod;

public class SaleDTO {

	private Long id;
	
	private Date saleDate; 
	
	private BigDecimal subtotal;

	private BigDecimal discount;
	
	private BigDecimal shippingCost;
	
	private BigDecimal totalAmount;

	private List<ProductSoldDTO> productsSoldDTO = new ArrayList<>();
	
	private DiscountCoupon discountCoupon;
	
	private PaymentMethod paymentMethod;
	
	private Individual individual;
	
	private LegalEntity legalEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getSaleDate() {
		return saleDate;
	}
	
	public void setSaleDate(Date saleDate) {
		this.saleDate = saleDate;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getShippingCost() {
		return shippingCost;
	}

	public void setShippingCost(BigDecimal shippingCost) {
		this.shippingCost = shippingCost;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<ProductSoldDTO> getProductsSoldDTO() {
		return productsSoldDTO;
	}

	public void setProductsSoldDTO(List<ProductSoldDTO> productsSoldDTO) {
		this.productsSoldDTO = productsSoldDTO;
	}

	public DiscountCoupon getDiscountCoupon() {
		return discountCoupon;
	}

	public void setDiscountCoupon(DiscountCoupon discountCoupon) {
		this.discountCoupon = discountCoupon;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Individual getIndividual() {
		return individual;
	}

	public void setIndividual(Individual individual) {
		this.individual = individual;
	}

	public LegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}

}
