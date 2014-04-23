package com.bertazoli.charity.server.oracle.definition;

public interface OracleDefinition {
    String getSelect();
    String getTable();
    String getWhere(String params);
}
