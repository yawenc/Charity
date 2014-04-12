package com.bertazoli.charity.shared.beans.oracle;

import java.util.List;

public class OracleListLoadResultBean implements OracleListLoadResult {
    private static final long serialVersionUID = -2991404333272107877L;
    private List<Country> data;
    private int totalLength;
    private int offSet;

    @Override
    public int getTotalLength() {
        return totalLength;
    }

    @Override
    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public List<Country> getData() {
        return data;
    }

    @Override
    public void setData(List<Country> data) {
        this.data = data;
    }

    @Override
    public int getOffset() {
        return offSet;
    }

    @Override
    public void setOffset(int offset) {
        this.offSet = offset;
    }

}
