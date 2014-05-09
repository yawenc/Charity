package com.bertazoli.charity.server.oracle.definition;

import com.bertazoli.charity.shared.util.Util;


public class StateOracleDefinition extends OracleDefinition {
    @Override
    public String getSelect() {
        return "a";
    }

    @Override
    public String getTable() {
        return "State a";
    }

    @Override
    public String getWhere(String query) {
        if (!Util.isNullOrEmpty(query)) {
            StringBuilder sb = new StringBuilder();
            sb.append("WHERE (UPPER(name) LIKE '%");
            sb.append(query.trim().toUpperCase());
            sb.append("%' OR UPPER(code) = '");
            sb.append(query.trim().toUpperCase());
            sb.append("')");
            
            if (filter != null && filter.getParam() != null) {
                sb.append(" AND countryId = " + filter.getParam());
            }
            return sb.toString();
        }
        return "";
    }
}
