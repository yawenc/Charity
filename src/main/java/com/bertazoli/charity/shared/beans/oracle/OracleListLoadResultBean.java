package com.bertazoli.charity.shared.beans.oracle;

import java.util.List;

import com.bertazoli.charity.client.application.oracle.IsOracleData;

public class OracleListLoadResultBean implements OracleListLoadResult {
    private static final long serialVersionUID = -2991404333272107877L;
    private List<IsOracleData> data;
    private int totalLength;
    private int offSet;
    
    @Override
    public List<IsOracleData> getData() {
        return data;
    }

    @Override
    public void setData(List<IsOracleData> data) {
        this.data = data;
    }

    @Override
    public int getOffset() {
        return offSet;
    }

    @Override
    public int getTotalLength() {
        return totalLength;
    }

    @Override
    public void setOffset(int offset) {
        this.offSet = offset;
    }

    @Override
    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }
}
