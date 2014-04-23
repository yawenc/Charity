package com.bertazoli.charity.client.application.oracle;

import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface OracleProperties extends PropertyAccess<IsOracleData> {
    ModelKeyProvider<IsOracleData> id();
    LabelProvider<IsOracleData> description();
}
