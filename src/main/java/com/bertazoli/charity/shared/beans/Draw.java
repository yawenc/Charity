package com.bertazoli.charity.shared.beans;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bertazoli.charity.shared.beans.enums.DrawStatus;
import com.google.gwt.user.client.rpc.IsSerializable;

@Entity
@Table(name = "draw")
public class Draw implements IsSerializable {
    @Id @GeneratedValue private Long id;
    private Timestamp drawDateStart;
    private Timestamp drawDateEnd;
    private DrawStatus status;
    private Boolean active;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Timestamp getDrawDateStart() {
        return drawDateStart;
    }
    public void setDrawDateStart(Timestamp drawDateStart) {
        this.drawDateStart = drawDateStart;
    }
    public Timestamp getDrawDateEnd() {
        return drawDateEnd;
    }
    public void setDrawDateEnd(Timestamp drawDateEnd) {
        this.drawDateEnd = drawDateEnd;
    }
    public DrawStatus getStatus() {
        return status;
    }
    public void setStatus(DrawStatus status) {
        this.status = status;
    }
    public Boolean getActive() {
        return active;
    }
    public void setActive(Boolean active) {
        this.active = active;
    }
}
