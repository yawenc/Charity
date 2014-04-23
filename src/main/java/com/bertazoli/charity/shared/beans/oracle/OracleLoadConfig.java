package com.bertazoli.charity.shared.beans.oracle;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.sencha.gxt.data.shared.loader.PagingLoadConfig;

public interface OracleLoadConfig extends PagingLoadConfig, IsSerializable {
    String getQuery();
    void setQuery(String query);
}
