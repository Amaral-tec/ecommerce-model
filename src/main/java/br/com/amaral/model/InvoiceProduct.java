package br.com.amaral.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "invoiced_products")
@SequenceGenerator(name = "seq_invoice_product", sequenceName = "seq_invoice_product", allocationSize = 1, initialValue = 1)
public class InvoiceProduct implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_invoice_product")
	private Long id;

	@NotNull
	@Column(nullable = false)
	private Double quantity;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	
	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false,
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "product_fk"))
	private Product product;
	
	@ManyToOne(targetEntity = AccountPayable.class)
	@JoinColumn(name = "purchase_invoice_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "purchase_invoice_id_fk"))
	private PurchaseInvoice purchaseInvoice;
	
	@ManyToOne(targetEntity = LegalEntity.class)
	@JoinColumn(name = "legal_entity_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "legal_entity_fk"))
	private LegalEntity legalEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public PurchaseInvoice getPurchaseInvoice() {
		return purchaseInvoice;
	}

	public void setPurchaseInvoice(PurchaseInvoice purchaseInvoice) {
		this.purchaseInvoice = purchaseInvoice;
	}

	public LegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(LegalEntity legalEntity) {
		this.legalEntity = legalEntity;
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
		InvoiceProduct other = (InvoiceProduct) obj;
		return Objects.equals(id, other.id);
	}
	
}
