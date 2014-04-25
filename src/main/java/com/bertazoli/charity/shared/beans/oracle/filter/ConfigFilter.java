package com.bertazoli.charity.shared.beans.oracle.filter;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ConfigFilter implements IsSerializable {

    private String param;

    public void setParam(String param) {
        this.param = param;
    }

    public String getParam() {
        return this.param;
    }
}
