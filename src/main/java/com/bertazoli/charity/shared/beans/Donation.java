package com.bertazoli.charity.shared.beans;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Donation implements IsSerializable {
    private Long userId;
    private Integer amountToDonate;
    private Integer percentageToKeep;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Integer getAmountToDonate() {
        return amountToDonate;
    }
    public void setAmountToDonate(Integer amountToDonate) {
        this.amountToDonate = amountToDonate;
    }
    public Integer getPercentageToKeep() {
        return percentageToKeep;
    }
    public void setPercentageToKeep(Integer percentageToKeep) {
        this.percentageToKeep = percentageToKeep;
    }
}
