package com.bertazoli.charity.shared.beans.enums;


public enum CharityDesignationCode {
    A("Public Foundation"),
    B("Private Foundation"),
    C("Charitable Organization");
    
    private String description;
    CharityDesignationCode(String description) {
        this.description = description;
    }
    public static CharityDesignationCode getFromString(String string) {
        for (CharityDesignationCode value : values()) {
            if (value.name().equalsIgnoreCase(string)) {
                return value;
            }
        }
        return null;
    }
}
