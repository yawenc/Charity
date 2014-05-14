package com.bertazoli.charity.shared.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "donation_information")
public class DonationInformation implements IsSerializable {
    @Id @GeneratedValue private Long id;
    private Long userId;
    private Long drawId;
    private Long charityId;
    private String paypalToken;
    private Integer amountToDonate;
    private Integer percentageToKeep;
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
    public Long getDrawId() {
        return drawId;
    }
    public void setDrawId(Long drawId) {
        this.drawId = drawId;
    }
    public Long getCharityId() {
        return charityId;
    }
    public void setCharityId(Long charityId) {
        this.charityId = charityId;
    }
    public String getPaypalToken() {
        return paypalToken;
    }
    public void setPaypalToken(String paypalToken) {
        this.paypalToken = paypalToken;
    }
}
