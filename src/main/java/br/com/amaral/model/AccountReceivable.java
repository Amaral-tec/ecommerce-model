package br.com.amaral.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.amaral.enums.AccountReceivableStatus;

@Entity
@Table(name = "accounts_receivable")
@SequenceGenerator(name = "seq_account_receivable", sequenceName = "seq_account_receivable", allocationSize = 1, initialValue = 1)
public class AccountReceivable implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account_receivable")
	private Long id;
	
	@NotBlank
	@Column(nullable = false)
	private String description;
	
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountReceivableStatus status;
	
	@NotNull
	@Column(name = "invoice_amount", nullable = false)
	private BigDecimal invoiceAmount;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "invoice_due_date", nullable = false)
	private Date invoiceDueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "payment_date")
	private Date paymentDate;
	
	@NotNull
	@Column(name = "payment_amount", nullable = false)
	private BigDecimal paymentAmount;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AccountReceivableStatus getStatus() {
		return status;
	}

	public void setStatus(AccountReceivableStatus status) {
		this.status = status;
	}

	public BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(BigDecimal invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Date getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BigDecimal getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(BigDecimal paymentAmount) {
		this.paymentAmount = paymentAmount;
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
		AccountReceivable other = (AccountReceivable) obj;
		return Objects.equals(id, other.id);
	}
	
}
