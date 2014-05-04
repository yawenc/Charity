package com.bertazoli.charity.client.application.login;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class LoginView extends ViewImpl implements LoginPresenter.MyView {
    interface Binder extends UiBinder<Widget, LoginView> {
    }

    @UiField TextField username;
    @UiField PasswordField password;
    @UiField TextButton submit;
    @UiField FormPanel mainPanel;

    @Inject
    LoginView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
        username.setAllowBlank(false);
        password.setAllowBlank(false);
    }

	@Override
	public HasSelectHandlers getSubmitButton() {
		return submit;
	}

	@Override
	public boolean validate() {
	    return mainPanel.isValid();
	}

	@Override
	public String getPassword() {
		return password.getText();
	}

	@Override
	public String getUsername() {
		return username.getText();
	}

    @Override
    public void setFocusToUsername() {
        username.focus();
    }
}
