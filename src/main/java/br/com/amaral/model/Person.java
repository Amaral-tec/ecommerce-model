package br.com.amaral.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import br.com.amaral.enums.TypeAddress;
import br.com.amaral.enums.TypePerson;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@SequenceGenerator(name = "seq_person", sequenceName = "seq_person", initialValue = 1, allocationSize = 1)
public abstract class Person implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person")
	private Long id;

	@Column(name = "type_person", nullable = false)
	@Enumerated(EnumType.STRING)
	private TypePerson typePerson;
	
	@NotBlank
	@Column(nullable = false)
	private String name;

	@Email
	@Column(nullable = false)
	private String email;

	@NotBlank
	@Column(nullable = false)
	private String phone;

	@Temporal(TemporalType.DATE)
	@Column(name = "created_at")
	private Date createdAt;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted = Boolean.FALSE;

	@OneToMany(mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Address> addresses = new ArrayList<>();

	
	public Address shippingAddress() {

		Address result = null;
		for (Address address : addresses) {
			if (address.getTypeAddress().name().equals(TypeAddress.SHIPPING.name())) {
				result = address;
				break;
			}
		}
		return result;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypePerson getTypePerson() {
		return typePerson;
	}

	public void setTypePerson(TypePerson typePerson) {
		this.typePerson = typePerson;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
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
		Person other = (Person) obj;
		return Objects.equals(id, other.id);
	}

}
