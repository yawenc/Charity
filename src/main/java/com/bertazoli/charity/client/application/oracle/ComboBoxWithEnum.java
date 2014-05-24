package com.bertazoli.charity.client.application.oracle;

import java.util.List;

import com.google.inject.Inject;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;

public class ComboBoxWithEnum {
    private SimpleComboBox<IsOracleData> combobox;
    
    @Inject
    public ComboBoxWithEnum() {
        combobox = new SimpleComboBox<IsOracleData>(new LabelProvider<IsOracleData>() {
            @Override
            public String getLabel(IsOracleData item) {
                return item.description();
            }
        });
    }
    
    public ComboBox<IsOracleData> getComboBox() {
        return combobox;
    }

    public void addData(List<IsOracleData> list) {
        combobox.add(list);
    }
}
