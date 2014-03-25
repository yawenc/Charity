package com.bertazoli.charity.client.application.signup;

import java.sql.Timestamp;

import javax.inject.Inject;

import com.bertazoli.charity.shared.beans.User;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtplatform.mvp.client.ViewImpl;

public class SignupView extends ViewImpl implements SignupPresenter.MyView {
    interface Binder extends UiBinder<Widget, SignupView> {}

    @UiField TextBox username;
    @UiField TextBox firstname;
    @UiField TextBox lastname;
    @UiField PasswordTextBox password;
    @UiField PasswordTextBox confirmPassword;
    @UiField TextBox email;
    @UiField DateBox dob;
    @UiField Button send;

    @Inject
    SignupView(Binder uiBinder) {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @Override
    public HasClickHandlers getSendButton() {
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
