package com.bertazoli.charity.client.application.usersettings;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class UserSettingsView extends ViewImpl implements UserSettingsPresenter.MyView {
    interface Binder extends UiBinder<Widget, UserSettingsView> {
    }

    @UiField
    SimplePanel main;

    @Inject
    UserSettingsView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == UserSettingsPresenter.SLOT_UserSettings) {
            main.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
