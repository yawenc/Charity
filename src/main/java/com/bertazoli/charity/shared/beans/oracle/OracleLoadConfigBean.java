package com.bertazoli.charity.shared.beans.oracle;

import java.util.List;

import com.sencha.gxt.data.shared.SortInfo;

public class OracleLoadConfigBean implements OracleLoadConfig {

    private static final long serialVersionUID = 2642152018515577197L;
    private int limit;
    private List<? extends SortInfo> info;
    private String query;
    private int offset;

    @Override
    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public int getLimit() {
        return limit;
    }

    @Override
    public List<? extends SortInfo> getSortInfo() {
        return info;
    }

    @Override
    public void setSortInfo(List<? extends SortInfo> info) {
        this.info = info;
    }

    @Override
    public String getQuery() {
        return query;
    }

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

}
