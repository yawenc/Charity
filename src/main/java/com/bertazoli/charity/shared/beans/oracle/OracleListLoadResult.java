package com.bertazoli.charity.shared.beans.oracle;

import java.util.List;

import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.google.gwt.user.client.rpc.IsSerializable;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

public interface OracleListLoadResult extends PagingLoadResult<IsOracleData>, IsSerializable {
    void setData(List<IsOracleData> data);
}
