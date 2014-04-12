package com.bertazoli.charity.shared.action;

import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResult;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfig;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class Oracle {
    @In(1) OracleLoadConfig params;
    @Out(1) OracleListLoadResult result;
}
