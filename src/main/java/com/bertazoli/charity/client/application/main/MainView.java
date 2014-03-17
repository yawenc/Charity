package com.bertazoli.charity.client.application.main;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.gwtplatform.mvp.client.ViewWithUiHandlers;

public class MainView extends ViewImpl implements MainPresenter.MyView {
    interface Binder extends UiBinder<Widget, MainView> {
    }

    @UiField
    SimplePanel main;

    @Inject
    MainView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == MainPresenter.SLOT_Main) {
            main.setWidget(content);
        } else {
            super.setInSlot(slot, content);
        }
    }
}
