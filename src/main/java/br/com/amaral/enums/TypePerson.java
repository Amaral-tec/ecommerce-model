package br.com.amaral.enums;

public enum TypePerson {

	CUSTOMER("Custumer"),
    SUPPLIER("Supplier"),
    PARTNER("Partner"),
    LEGAL("Legal Entity"),
    INDIVIDUAL("Individual");
    
	private String displayName;

	private TypePerson(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

}
