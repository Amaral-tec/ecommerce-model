package br.com.amaral.enums;

public enum Size {
	S("Small"),
    M("Medium"),
    L("Large"),
    XL("X-Large"),
    XXL("XX-Large");

    private final String displayName;

    Size(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
