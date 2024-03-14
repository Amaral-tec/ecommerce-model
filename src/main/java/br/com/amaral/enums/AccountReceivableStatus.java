package br.com.amaral.enums;

public enum AccountReceivableStatus {
    OPEN("Open"),
    PAID("Paid"),
    OVERDUE("Overdue"),
    CLOSED("Closed"),
    CANCELED("Canceled");

    private String displayName;

    AccountReceivableStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
