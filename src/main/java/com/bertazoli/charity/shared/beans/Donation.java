package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import urn.ebay.apis.eBLBaseComponents.CurrencyCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentCodeType;
import urn.ebay.apis.eBLBaseComponents.PaymentStatusCodeType;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "donation")
public class Donation implements IsSerializable {
    @Id @GeneratedValue private Long id;
    private Long userId;
    private Long drawId;
    private Long charityId;
    private Timestamp donationDate;
    private String transaction;
    private CurrencyCodeType feeAmountCurrency;
    private Double feeAmountValue;
    private CurrencyCodeType grossAmountCurrency;
    private Double grossAmountValue;
    private PaymentStatusCodeType paymentStatus;
    private PaymentCodeType paymentType;
    private Boolean completed;
    private String paypalToken;
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
    public String getTransaction() {
        return transaction;
    }
    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
    public CurrencyCodeType getFeeAmountCurrency() {
        return feeAmountCurrency;
    }
    public void setFeeAmountCurrency(CurrencyCodeType feeAmountCurrency) {
        this.feeAmountCurrency = feeAmountCurrency;
    }
    public Double getFeeAmountValue() {
        return feeAmountValue;
    }
    public void setFeeAmountValue(Double feeAmountValue) {
        this.feeAmountValue = feeAmountValue;
    }
    public CurrencyCodeType getGrossAmountCurrency() {
        return grossAmountCurrency;
    }
    public void setGrossAmountCurrency(CurrencyCodeType grossAmountCurrency) {
        this.grossAmountCurrency = grossAmountCurrency;
    }
    public Double getGrossAmountValue() {
        return grossAmountValue;
    }
    public void setGrossAmountValue(Double grossAmountValue) {
        this.grossAmountValue = grossAmountValue;
    }
    public PaymentStatusCodeType getPaymentStatus() {
        return paymentStatus;
    }
    public void setPaymentStatus(PaymentStatusCodeType paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public PaymentCodeType getPaymentType() {
        return paymentType;
    }
    public void setPaymentType(PaymentCodeType paymentType) {
        this.paymentType = paymentType;
    }
    public Boolean getCompleted() {
        return completed;
    }
    public void setCompleted(Boolean completed) {
        this.completed = completed;
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
