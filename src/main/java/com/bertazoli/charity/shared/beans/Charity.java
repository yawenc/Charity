package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.bertazoli.charity.shared.beans.enums.CharityCategory;
import com.bertazoli.charity.shared.beans.enums.CharityDesignationCode;
import com.bertazoli.charity.shared.beans.enums.CharitySanction;
import com.bertazoli.charity.shared.beans.enums.CharityStatus;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "charity")
public class Charity implements IsSerializable, IsOracleData {

    @Id @GeneratedValue private Long id;
    private String registrationNumber;
    private String name;
    private CharityStatus status;
    private CharitySanction sanction;
    private CharityDesignationCode designationCode;
    private Timestamp effectiveDateOfStatus;
    private CharityCategory category;
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
    public Timestamp getEffectiveDateOfStatus() {
        return effectiveDateOfStatus;
    }
    public void setEffectiveDateOfStatus(Timestamp effectiveDateOfStatus) {
        this.effectiveDateOfStatus = effectiveDateOfStatus;
    }
    public CharityCategory getCategory() {
        return category;
    }
    public void setCategory(CharityCategory category) {
        this.category = category;
    }
    @Override
    public String id() {
        return String.valueOf(id);
    }
    @Override
    public String description() {
        return getName();
    }
    public CharityStatus getStatus() {
        return status;
    }
    public void setStatus(CharityStatus status) {
        this.status = status;
    }
    public CharitySanction getSanction() {
        return sanction;
    }
    public void setSanction(CharitySanction sanction) {
        this.sanction = sanction;
    }
    public CharityDesignationCode getDesignationCode() {
        return designationCode;
    }
    public void setDesignationCode(CharityDesignationCode designationCode) {
        this.designationCode = designationCode;
    }
}
