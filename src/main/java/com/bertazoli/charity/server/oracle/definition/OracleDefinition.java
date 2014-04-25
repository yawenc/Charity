package com.bertazoli.charity.server.oracle.definition;

import com.bertazoli.charity.shared.beans.oracle.filter.ConfigFilter;

public abstract class OracleDefinition {
    protected ConfigFilter filter;
    
    public abstract String getSelect();
    public abstract String getTable();
    public abstract String getWhere(String params);
    public void setFilter(ConfigFilter filter) {
        this.filter = filter;
    }
}
