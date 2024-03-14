package br.com.amaral.model;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.br.CPF;

@Entity
@Table(name = "individual")
@PrimaryKeyJoinColumn(name = "id")
public class Individual extends Person {

	private static final long serialVersionUID = 1L;
	
	@CPF
	@Column(nullable = false)
	private String cpf;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_birth")
	private Date dateBirth;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDateBirth() {
		return dateBirth;
	}

	public void setDateBirth(Date dateBirth) {
		this.dateBirth = dateBirth;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cpf);
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
		Individual other = (Individual) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
}
