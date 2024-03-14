package br.com.amaral.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class JunoBankSlipDataDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String code;
	private String reference;
	private String dueDate;
	private String link;
	private String checkoutUrl;
	private String installmentLink;
	private String payNumber;
	private String amount;
	private String status;

	private JunoBilletDetailsDTO billetDetails = new JunoBilletDetailsDTO();

	private List<JunoPaymentsDTO> payments = new ArrayList<>();

	private JunoPixDTO pix = new JunoPixDTO();

	private List<JunoLinksDTO> _links = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
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

	public String getInstallmentLink() {
		return installmentLink;
	}

	public void setInstallmentLink(String installmentLink) {
		this.installmentLink = installmentLink;
	}

	public String getPayNumber() {
		return payNumber;
	}

	public void setPayNumber(String payNumber) {
		this.payNumber = payNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JunoBilletDetailsDTO getBilletDetails() {
		return billetDetails;
	}

	public void setBilletDetails(JunoBilletDetailsDTO billetDetails) {
		this.billetDetails = billetDetails;
	}

	public List<JunoPaymentsDTO> getPayments() {
		return payments;
	}

	public void setPayments(List<JunoPaymentsDTO> payments) {
		this.payments = payments;
	}

	public JunoPixDTO getPix() {
		return pix;
	}

	public void setPix(JunoPixDTO pix) {
		this.pix = pix;
	}

	public List<JunoLinksDTO> get_links() {
		return _links;
	}

	public void set_links(List<JunoLinksDTO> _links) {
		this._links = _links;
	}
	
	
}
