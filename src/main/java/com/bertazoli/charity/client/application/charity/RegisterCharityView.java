package com.bertazoli.charity.client.application.charity;

import javax.inject.Inject;

import com.bertazoli.charity.client.application.oracle.ComboBoxWithOracle;
import com.bertazoli.charity.client.application.oracle.IsOracleData;
import com.bertazoli.charity.shared.beans.oracle.filter.ConfigFilter;
import com.bertazoli.charity.shared.beans.oracle.type.CountryOracleConfig;
import com.bertazoli.charity.shared.beans.oracle.type.StateOracleConfig;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.TextField;

public class RegisterCharityView extends ViewImpl implements RegisterCharityPresenter.MyView {
    interface Binder extends UiBinder<Widget, RegisterCharityView> {}

    @UiField FormPanel mainPanel;
    @UiField TextField name;
    @UiField TextField registrationNumber;
    @UiField DateField effectiveDateOfStatus;
    @UiField TextField category;
    @UiField TextButton send;
    @UiField SimplePanel countryPanel;
    @UiField SimplePanel statePanel;
    private ComboBoxWithOracle<CountryOracleConfig> countryCombobox;
    private ComboBoxWithOracle<StateOracleConfig> stateCombobox;

    @Inject
    RegisterCharityView(Binder uiBinder,
            Provider<ComboBoxWithOracle<CountryOracleConfig>> countryComboProvider,
            Provider<ComboBoxWithOracle<StateOracleConfig>> stateComboProvider) {
        initWidget(uiBinder.createAndBindUi(this));
        countryCombobox = countryComboProvider.get();
        countryPanel.add(countryCombobox.getComboBox());
        stateCombobox = stateComboProvider.get();
        statePanel.add(stateCombobox.getComboBox());
        
        countryCombobox.getComboBox().addValueChangeHandler(new ValueChangeHandler<IsOracleData>() {
            @Override
            public void onValueChange(ValueChangeEvent<IsOracleData> event) {
                ConfigFilter filter = new ConfigFilter();
                filter.setParam(event != null ? event.getValue().id() : null);
                stateCombobox.filter(filter);
            }
        });
    }
}
