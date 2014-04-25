package com.bertazoli.charity.shared.beans;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "state")
public class State implements IsSerializable, IsOracleData {
    @Id @GeneratedValue private Long id;
    private Long countryId;
    private String name;
    private String code;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String id() {
        return String.valueOf(getId());
    }
    @Override
    public String description() {
        return name;
    }
    public Long getCountryId() {
        return countryId;
    }
    public void setCountryId(Long countryId) {
        this.countryId = countryId;
    }
}
