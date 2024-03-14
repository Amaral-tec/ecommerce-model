package br.com.amaral.enums;

public enum TypeAddress {

	BILLING("Billing"),
	SHIPPING("Shipping");
	
	private String displayName;

	private TypeAddress(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
	
}
