package br.com.amaral.enums;

public enum SaleStatus {
    PENDING("Pending"),
    COMPLETED("Completed"),
    CANCELED("Canceled"),
    REFUNDED("Refunded"),
    CART("In Cart");

    private final String displayName;

    SaleStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

