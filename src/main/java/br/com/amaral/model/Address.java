package br.com.amaral.model;

import java.io.Serializable;
import java.util.Objects;

import br.com.amaral.enums.TypeAddress;
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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "addresses")
@SequenceGenerator(name = "seq_address", sequenceName = "seq_address", allocationSize = 1, initialValue = 1)
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_address")
	private Long id;
	
	@NotBlank
	@Column(name= "street_address", nullable = false)
	private String streetAddress;
	
	@NotBlank
	@Column(nullable = false)
	private String number;
	
	private String complement;
	
	@NotBlank
	@Column(nullable = false)
	private String district;
	
	@NotBlank
	@Column(nullable = false)
	private String state;
	
	@NotBlank
	@Column(nullable = false)
	private String city;
	
	@Column(name = "country_Id")
	private String countryId = "55";
	
	@NotBlank
	@Column(name= "postal_code", nullable = false)
	private String postalCode;
	
	@NotNull
	@Column(name = "type_address", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypeAddress typeAddress;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;
	
	@JsonIgnore
	@ManyToOne(targetEntity = Person.class)
	@JoinColumn(name = "person_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT, name = "person_fk"))
	private Person person;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getComplement() {
		return complement;
	}

	public void setComplement(String complement) {
		this.complement = complement;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public TypeAddress getTypeAddress() {
		return typeAddress;
	}

	public void setTypeAddress(TypeAddress typeAddress) {
		this.typeAddress = typeAddress;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
		Address other = (Address) obj;
		return Objects.equals(id, other.id);
	}
	
}
