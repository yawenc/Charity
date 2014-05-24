package com.bertazoli.charity.client.application.usersettings;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class UserSettingsView extends ViewImpl implements UserSettingsPresenter.MyView {
    interface Binder extends UiBinder<Widget, UserSettingsView> {
    }
    
    @UiField HTMLPanel donationList;

    @Inject
    UserSettingsView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
