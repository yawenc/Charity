package com.bertazoli.charity.shared.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "user_ticket")
public class UserTicket implements IsSerializable {
    @Id @GeneratedValue private Long id;
    private Long donationId;
    private String ticketNumber;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getDonationId() {
        return donationId;
    }
    public void setDonationId(Long donationId) {
        this.donationId = donationId;
    }
    public String getTicketNumber() {
        return ticketNumber;
    }
    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }
}
