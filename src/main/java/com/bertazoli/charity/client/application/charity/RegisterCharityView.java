package com.bertazoli.charity.client.application.charity;

import javax.inject.Inject;

import com.bertazoli.charity.client.application.oracle.ComboBoxWithOracle;
import com.bertazoli.charity.shared.beans.oracle.type.CountryOracleConfig;
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
    @UiField SimplePanel comboPanel;

    @Inject
    RegisterCharityView(Binder uiBinder, Provider<ComboBoxWithOracle<CountryOracleConfig>> comboboxProvider) {
        initWidget(uiBinder.createAndBindUi(this));
        comboPanel.add(comboboxProvider.get().getComboBox());
    }
}
