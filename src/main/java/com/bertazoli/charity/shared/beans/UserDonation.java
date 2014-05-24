package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDonation implements IsSerializable {
    private Long id;
    private Long userId;
    private Long drawId;
    private Timestamp donationDate;
    private Double grossAmountValue;
    private String charityName;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getDrawId() {
        return drawId;
    }
    public void setDrawId(Long drawId) {
        this.drawId = drawId;
    }
    public Timestamp getDonationDate() {
        return donationDate;
    }
    public void setDonationDate(Timestamp donationDate) {
        this.donationDate = donationDate;
    }
    public Double getGrossAmountValue() {
        return grossAmountValue;
    }
    public void setGrossAmountValue(Double grossAmountValue) {
        this.grossAmountValue = grossAmountValue;
    }
    public String getCharityName() {
        return charityName;
    }
    public void setCharityName(String charityName) {
        this.charityName = charityName;
    }
}
