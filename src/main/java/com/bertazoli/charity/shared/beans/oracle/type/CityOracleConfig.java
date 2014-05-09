package com.bertazoli.charity.shared.beans.oracle.type;


import com.bertazoli.charity.shared.beans.oracle.OracleLoadConfigBean;
import com.bertazoli.charity.shared.oracle.DataType;
import com.google.inject.Inject;

public class CityOracleConfig extends OracleLoadConfigBean {

    @Inject
    public CityOracleConfig() {
        super(DataType.CITY);
    }
}
