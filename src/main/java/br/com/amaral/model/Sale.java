package br.com.amaral.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.amaral.enums.SaleStatus;

@Entity
@Table(name = "sales")
@SequenceGenerator(name = "seq_sale", sequenceName = "seq_sale", allocationSize = 1, initialValue = 1)
public class Sale implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sale")
	private Long id;
	
	@NotNull
	@Column(name = "sale_date", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date saleDate;
	
	@NotNull
	@Column(nullable = false)
	private BigDecimal subtotal;

	private BigDecimal discount;
	
	@Column(name = "shipping_cost")
	private BigDecimal shippingCost;
	
	@Column(name = "total_amount", nullable = false)
	private BigDecimal totalAmount;
	
	@NotNull
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private SaleStatus status;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	
	@OneToMany(mappedBy = "sale", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<ProductSold> productsSold = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "discount_coupon_id", 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "discount_coupon_fk"))
	private DiscountCoupon discountCoupon;
	
	@ManyToOne
	@JoinColumn(name = "payment_method_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "payment_method_fk"))
	private PaymentMethod paymentMethod;
	
	@JsonIgnoreProperties(allowGetters = true)
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "sales_invoice_id", nullable = true, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "sales_invoice_fk"))
	private SalesInvoice salesInvoice;
	
	@ManyToOne(targetEntity = Individual.class)
	@JoinColumn(name = "individual_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "individual_id_fk"))
	private Individual individual;
	
	@ManyToOne(targetEntity = LegalEntity.class)
	@JoinColumn(name = "legal_entity_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "legal_entity_fk"))
	private LegalEntity legalEntity;
	
	/* Information related to product shipping */
	@Column(name = "estimated_shipping_days")
	private Integer estimatedShippingDays;

	@Column(name = "estimated_delivery_date")
	@Temporal(TemporalType.DATE)
	private Date estimatedDeliveryDate;
	
	@Column(name = "tracking_number")
	private String trackingNumber;
	
	@Column(name = "label_url")
	private String labelUrl;
	
	@Column(name = "shipping_company")
	private String shippingCompany;

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

	public SaleStatus getStatus() {
		return status;
	}

	public void setStatus(SaleStatus status) {
		this.status = status;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<ProductSold> getProductsSold() {
		return productsSold;
	}

	public void setProductsSold(List<ProductSold> productsSold) {
		this.productsSold = productsSold;
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

	public SalesInvoice getSalesInvoice() {
		return salesInvoice;
	}

	public void setSalesInvoice(SalesInvoice salesInvoice) {
		this.salesInvoice = salesInvoice;
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

	public Integer getEstimatedShippingDays() {
		return estimatedShippingDays;
	}

	public void setEstimatedShippingDays(Integer estimatedShippingDays) {
		this.estimatedShippingDays = estimatedShippingDays;
	}

	public Date getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getLabelUrl() {
		return labelUrl;
	}

	public void setLabelUrl(String labelUrl) {
		this.labelUrl = labelUrl;
	}

	public String getShippingCompany() {
		return shippingCompany;
	}

	public void setShippingCompany(String shippingCompany) {
		this.shippingCompany = shippingCompany;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		return Objects.equals(id, other.id);
	}
	
}
