package com.bertazoli.charity.shared.action;

import com.bertazoli.charity.shared.beans.oracle.OracleListLoadResultBean;
import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfigBean;
import com.gwtplatform.dispatch.annotation.GenDispatch;
import com.gwtplatform.dispatch.annotation.In;
import com.gwtplatform.dispatch.annotation.Out;

@GenDispatch(isSecure=false)
public class Oracle {
    @In(1) OracleLoadConfigBean params;
    @Out(1) OracleListLoadResultBean result;
}
