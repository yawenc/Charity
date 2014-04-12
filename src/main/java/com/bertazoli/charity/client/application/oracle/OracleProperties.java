package com.bertazoli.charity.client.application.oracle;

import com.bertazoli.charity.shared.beans.oracle.Country;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;

public interface OracleProperties extends PropertyAccess<Country> {
    ModelKeyProvider<Country> id();
    LabelProvider<Country> description();
}