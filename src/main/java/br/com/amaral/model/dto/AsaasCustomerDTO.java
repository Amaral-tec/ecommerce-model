package br.com.amaral.model.dto;

import java.io.Serializable;

public class AsaasCustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String phone;
	private String mobilePhone;
	private String address;
	private String addressNumber;
	private String complement;
	private String province;
	private String postalCode;
	private String cpfCnpj;
	private String personType;
	private Boolean deleted;
	private String additionalEmails;
	private String externalReference;
	private Boolean notificationDisabled;
	private Integer city;
	private String state;
	private String country;
	private String observations;
	
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
	
	public String getMobilePhone() {
		return mobilePhone;
	}
	
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getAddressNumber() {
		return addressNumber;
	}
	
	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}
	
	public String getComplement() {
		return complement;
	}
	
	public void setComplement(String complement) {
		this.complement = complement;
	}
	
	public String getProvince() {
		return province;
	}
	
	public void setProvince(String province) {
		this.province = province;
	}
	
	public String getPostalCode() {
		return postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	public String getPersonType() {
		return personType;
	}
	
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	public Boolean getDeleted() {
		return deleted;
	}
	
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getAdditionalEmails() {
		return additionalEmails;
	}
	
	public void setAdditionalEmails(String additionalEmails) {
		this.additionalEmails = additionalEmails;
	}
	
	public String getExternalReference() {
		return externalReference;
	}
	
	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}
	
	public Boolean getNotificationDisabled() {
		return notificationDisabled;
	}
	
	public void setNotificationDisabled(Boolean notificationDisabled) {
		this.notificationDisabled = notificationDisabled;
	}
	
	public Integer getCity() {
		return city;
	}
	
	public void setCity(Integer city) {
		this.city = city;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getObservations() {
		return observations;
	}
	
	public void setObservations(String observations) {
		this.observations = observations;
	}
	
}
