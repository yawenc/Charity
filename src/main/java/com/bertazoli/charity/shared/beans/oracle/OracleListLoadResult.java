package com.bertazoli.charity.shared.beans.oracle;

import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface OracleListLoadResult extends PagingLoadResult<Country>, IsSerializable {
    void setData(List<Country> data);
}
