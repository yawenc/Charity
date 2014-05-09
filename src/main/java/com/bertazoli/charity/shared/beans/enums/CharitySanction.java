package com.bertazoli.charity.shared.beans.enums;


public enum CharitySanction {
    PENALIZED,
    SUSPENDED;

    public static CharitySanction getFromString(String string) {
        for (CharitySanction value : values()) {
            if (value.name().equalsIgnoreCase(string)) {
                return value;
            }
        }
        return null;
    }
}
