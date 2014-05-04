package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "charity")
public class Charity implements IsSerializable, IsOracleData {

    @Id @GeneratedValue private Long id;
    private String registrationNumber;
    private String name;
    private Long statusId;
    private Long sanctionId;
    private Character designationCode;
    private Timestamp effectiveDateOfStatus;
    private Long categoryCode;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getRegistrationNumber() {
        return registrationNumber;
    }
    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getStatusId() {
        return statusId;
    }
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
    public Long getSanctionId() {
        return sanctionId;
    }
    public void setSanctionId(Long sanctionId) {
        this.sanctionId = sanctionId;
    }
    public Character getDesignationCode() {
        return designationCode;
    }
    public void setDesignationCode(Character designationCode) {
        this.designationCode = designationCode;
    }
    public Timestamp getEffectiveDateOfStatus() {
        return effectiveDateOfStatus;
    }
    public void setEffectiveDateOfStatus(Timestamp effectiveDateOfStatus) {
        this.effectiveDateOfStatus = effectiveDateOfStatus;
    }
    public Long getCategoryCode() {
        return categoryCode;
    }
    public void setCategoryCode(Long categoryCode) {
        this.categoryCode = categoryCode;
    }
    @Override
    public String id() {
        return String.valueOf(id);
    }
    @Override
    public String description() {
        return getName();
    }
}
