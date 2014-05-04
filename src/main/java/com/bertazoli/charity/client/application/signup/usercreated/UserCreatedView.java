package com.bertazoli.charity.client.application.signup.usercreated;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;

public class UserCreatedView extends ViewImpl implements UserCreatedPresenter.MyView {
    interface Binder extends UiBinder<Widget, UserCreatedView> {}
    
    @UiField TextButton backButton;

    @Inject
    UserCreatedView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public HasSelectHandlers getBackButton() {
        return backButton;
    }
}
