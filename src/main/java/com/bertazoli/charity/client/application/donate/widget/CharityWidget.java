package com.bertazoli.charity.client.application.donate.widget;

import com.bertazoli.charity.shared.beans.Charity;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.sencha.gxt.widget.core.client.Composite;

public class CharityWidget extends Composite {
    interface Binder extends UiBinder<Widget, CharityWidget>{};
    
    @UiField Label name;
    @UiField Label registrationNumber;
    @UiField Label category;
    
    @Inject
    public CharityWidget(Binder binder) {
        initWidget(binder.createAndBindUi(this));
    }

    public void setBean(Charity bean) {
        name.setText(bean.getName());
        registrationNumber.setText(bean.getRegistrationNumber());
        category.setText(String.valueOf(bean.getCategoryCode()));
    }
}
