package com.bertazoli.charity.client.application.login;

import javax.inject.Inject;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class LoginView extends ViewImpl implements LoginPresenter.MyView {
    interface Binder extends UiBinder<Widget, LoginView> {
    }

    @UiField TextBox username;
    @UiField PasswordTextBox password;
    @UiField Button submit;

    @Inject
    LoginView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

	@Override
	public HasClickHandlers getSubmitButton() {
		return submit;
	}

	@Override
	public boolean validate() {
		return true;
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public String getUsername() {
		return username.getText();
	}
}
