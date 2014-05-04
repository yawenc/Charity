package com.bertazoli.charity.shared.searchparams;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CharitySearchParams implements IsSerializable {
    private int limit;
    private Long charityId;

    public int getLimit() {
        return limit;
    }
    public void setLimit(int limit) {
        this.limit = limit;
    }
    public Long getCharityId() {
        return charityId;
    }
    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }
}
