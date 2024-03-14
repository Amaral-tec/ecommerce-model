package br.com.amaral.model;

import java.io.Serializable;
import java.math.BigDecimal;
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

@Entity
@Table(name = "juno_bank_slip")
@SequenceGenerator(name = "seq_juno_bank_slip", sequenceName = "seq_juno_bank_slip", allocationSize = 1, initialValue = 1)
public class JunoBankSlip implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_juno_bank_slip")
	private Long id;

	private String code = "";

	private String link;

	@Column(name = "checkout_url")
	private String checkoutUrl = "";

	private boolean isPaid = false;

	@Column(name = "due_date")
	private String dueDate = "";
	
	private BigDecimal amount = BigDecimal.ZERO;

	private Integer recurrence = 0;

	/* Bank Slip control ID to be able to cancel via API */
	@Column(name = "id_chr_bank_slip")
	private String idChrBankSlip = "";

	/* Bank slip installment link */
	@Column(name = "installment_link")
	private String installmentLink = "";

	@Column(name = "id_pix")
	private String idPix = "";

	@Column(name = "payload_in_Base64", columnDefinition = "text")
	private String payloadInBase64 = "";

	@Column(name = "image_in_Base64", columnDefinition = "text")
	private String imageInBase64 = "";

	@Column(name = "charge_i_cart")
	private String chargeICart = "";

	@ManyToOne
	@JoinColumn(name = "sale_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "sale_fk"))
	private Sale sale;

	@ManyToOne(targetEntity = LegalEntity.class)
	@JoinColumn(name = "legal_entity_id", nullable = false, foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "legal_entity_fk"))
	private LegalEntity legalEntity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getCheckoutUrl() {
		return checkoutUrl;
	}

	public void setCheckoutUrl(String checkoutUrl) {
		this.checkoutUrl = checkoutUrl;
	}

	public boolean isPaid() {
		return isPaid;
	}

	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getRecurrence() {
		return recurrence;
	}

	public void setRecurrence(Integer recurrence) {
		this.recurrence = recurrence;
	}

	public String getIdChrBankSlip() {
		return idChrBankSlip;
	}

	public void setIdChrBankSlip(String idChrBankSlip) {
		this.idChrBankSlip = idChrBankSlip;
	}

	public String getInstallmentLink() {
		return installmentLink;
	}

	public void setInstallmentLink(String installmentLink) {
		this.installmentLink = installmentLink;
	}

	public String getIdPix() {
		return idPix;
	}

	public void setIdPix(String idPix) {
		this.idPix = idPix;
	}

	public String getPayloadInBase64() {
		return payloadInBase64;
	}

	public void setPayloadInBase64(String payloadInBase64) {
		this.payloadInBase64 = payloadInBase64;
	}

	public String getImageInBase64() {
		return imageInBase64;
	}

	public void setImageInBase64(String imageInBase64) {
		this.imageInBase64 = imageInBase64;
	}

	public String getChargeICart() {
		return chargeICart;
	}

	public void setChargeICart(String chargeICart) {
		this.chargeICart = chargeICart;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
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
		JunoBankSlip other = (JunoBankSlip) obj;
		return Objects.equals(id, other.id);
	}

}
