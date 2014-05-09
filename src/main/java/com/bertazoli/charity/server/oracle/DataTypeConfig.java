package com.bertazoli.charity.server.oracle;

import java.util.HashMap;

import com.bertazoli.charity.server.oracle.definition.CityOracleDefinition;
import com.bertazoli.charity.server.oracle.definition.CountryOracleDefinition;
import com.bertazoli.charity.server.oracle.definition.OracleDefinition;
import com.bertazoli.charity.server.oracle.definition.StateOracleDefinition;
import com.bertazoli.charity.shared.oracle.DataType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DataTypeConfig {
    HashMap<DataType, OracleDefinition> map = new HashMap<DataType, OracleDefinition>();
    
    @Inject
    public DataTypeConfig() {
        map.put(DataType.COUNTRY, new CountryOracleDefinition());
        map.put(DataType.STATE, new StateOracleDefinition());
        map.put(DataType.CITY, new CityOracleDefinition());
    }
    
    public OracleDefinition getDefinition(DataType type) {
        return map.get(type);
    }
}
