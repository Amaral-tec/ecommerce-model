package br.com.amaral.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "purchase_invoices")
@SequenceGenerator(name = "seq_purchase_invoice", sequenceName = "seq_purchase_invoice", allocationSize = 1, initialValue = 1)
public class PurchaseInvoice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_purchase_invoice")
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private String number;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_date", nullable = false)
    private Date invoiceDate;
	
	@NotNull
	@Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;
    
	@NotNull
	@Column(name = "tax_amount")
    private BigDecimal taxAmount;
    
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	
	@ManyToOne(targetEntity = Individual.class)
	@JoinColumn(name = "individual_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "individual_id_fk"))
	private Individual individual;
	
	@ManyToOne(targetEntity = LegalEntity.class)
	@JoinColumn(name = "legal_entity_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "legal_entity_fk"))
	private LegalEntity legalEntity;
	
	@ManyToOne
	@JoinColumn(name = "account_payable_id", nullable = false, 
	foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "account_payable_fk"))
	private AccountPayable accountPayable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
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

	public AccountPayable getAccountPayable() {
		return accountPayable;
	}

	public void setAccountPayable(AccountPayable accountPayable) {
		this.accountPayable = accountPayable;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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
		PurchaseInvoice other = (PurchaseInvoice) obj;
		return Objects.equals(id, other.id);
	}
	
}
