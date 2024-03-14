package br.com.amaral.enums;

public enum AccountPayableStatus {
    PENDING("Pending"),
    APPROVED("Approved"),
    PAID("Paid"),
    CANCELED("Canceled");

    private String displayName;

    AccountPayableStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
