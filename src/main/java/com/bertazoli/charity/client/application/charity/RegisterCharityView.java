package com.bertazoli.charity.client.application.charity;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class RegisterCharityView extends ViewImpl implements RegisterCharityPresenter.MyView {
    interface Binder extends UiBinder<Widget, RegisterCharityView> {}

    @Inject
    RegisterCharityView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }
}
