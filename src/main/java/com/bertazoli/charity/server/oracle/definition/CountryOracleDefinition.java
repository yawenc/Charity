package com.bertazoli.charity.server.oracle.definition;

import com.bertazoli.charity.shared.util.Util;


public class CountryOracleDefinition extends OracleDefinition {
    @Override
    public String getSelect() {
        return "a";
    }

    @Override
    public String getTable() {
        return "Country a";
    }

    @Override
    public String getWhere(String query) {
        if (!Util.isNullOrEmpty(query)) {
            return "WHERE (UPPER(name) LIKE '%"+query.trim().toUpperCase()+"%' OR UPPER(code) = '"+query.trim().toUpperCase()+"')";    
        }
        return "";
    }
}
