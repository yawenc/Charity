package com.bertazoli.charity.client.application.oracle;

import com.google.gwt.user.client.rpc.IsSerializable;

public interface IsOracleData extends IsSerializable {
    String id();
    String description();
}
