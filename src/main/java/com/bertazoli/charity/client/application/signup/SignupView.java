package com.bertazoli.charity.client.application.signup;

import java.sql.Timestamp;

import javax.inject.Inject;

import com.bertazoli.charity.shared.beans.User;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class SignupView extends ViewImpl implements SignupPresenter.MyView {
    interface Binder extends UiBinder<Widget, SignupView> {}

    @UiField TextField username;
    @UiField TextField firstname;
    @UiField TextField lastname;
    @UiField PasswordField password;
    @UiField PasswordField confirmPassword;
    @UiField TextField email;
    @UiField DateField dob;
    @UiField TextButton send;

    @Inject
    SignupView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public HasSelectHandlers getSendButton() {
        return send;
    }

    @Override
    public boolean validate() {
        // TODO validate
        return true;
    }

    @Override
    public User mapBean(User user) {
        user.setUsername(username.getText());
        user.setPassword(password.getText());
        user.setDob(new Timestamp(dob.getValue().getTime()));
        user.setEmail(email.getText());
        user.setFirstName(firstname.getText());
        user.setLastName(lastname.getText());
        return user;
    }
}
