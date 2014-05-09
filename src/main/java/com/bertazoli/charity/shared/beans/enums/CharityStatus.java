package com.bertazoli.charity.shared.beans.enums;


public enum CharityStatus {
    ANNULLED("Annulled"),
    REGISTERED("Registered"),
    REVOKED_FAILURE_TO_FILE("Revoked - Failure to file"),
    REVOKED_FOR_CAUSE("Revoked - For cause"),
    REVOKED_VOLUNTARY("Revoked - Voluntary");
    
    private String description;
    private CharityStatus(String description) {
        this.description = description;
    }

    public static CharityStatus getFromString(String string) {
        for (CharityStatus value : values()) {
            if (value.description.equalsIgnoreCase(string)) {
                return value;
            }
        }
        return null;
    }
}
