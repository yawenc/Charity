package com.bertazoli.charity.client.application.admin;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;

public class AdminView extends ViewImpl implements AdminPresenter.MyView {
    interface Binder extends UiBinder<Widget, AdminView> {}

    @UiField TextButton runDraw;

    @Inject
    AdminView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public HasSelectHandlers getRunDraw() {
        return runDraw;
    }
}
