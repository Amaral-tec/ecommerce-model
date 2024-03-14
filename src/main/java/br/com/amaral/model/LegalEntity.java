package br.com.amaral.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CNPJ;

@Entity
@Table(name = "legal_entities")
@PrimaryKeyJoinColumn(name = "id")
public class LegalEntity extends Person {

	private static final long serialVersionUID = 1L;
	
	@CNPJ
	@Column(nullable = false)
	private String cnpj;
	
	@NotBlank
	@Column(name = "trade_name", nullable = false)
	private String tradeName;

	@Column(name = "state_tax_id")
	private String stateTaxID;

	@Column(name = "district_tax_id")
	private String districtTaxID;

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTradeName() {
		return tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

	public String getStateTaxID() {
		return stateTaxID;
	}

	public void setStateTaxID(String stateTaxID) {
		this.stateTaxID = stateTaxID;
	}

	public String getDistrictTaxID() {
		return districtTaxID;
	}

	public void setDistrictTaxID(String districtTaxID) {
		this.districtTaxID = districtTaxID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cnpj);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LegalEntity other = (LegalEntity) obj;
		return Objects.equals(cnpj, other.cnpj);
	}

}
