package com.bertazoli.charity.client.application.signup;

import java.sql.Timestamp;
import java.util.Date;

import javax.inject.Inject;

import com.bertazoli.charity.shared.beans.User;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.HasSelectHandlers;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.form.validator.MaxDateValidator;
import com.sencha.gxt.widget.core.client.form.validator.MaxLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.MinLengthValidator;
import com.sencha.gxt.widget.core.client.form.validator.RegExValidator;

public class SignupView extends ViewImpl implements SignupPresenter.MyView {
    interface Binder extends UiBinder<Widget, SignupView> {}

    @UiField FormPanel mainPanel;
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
        username.setAllowBlank(false);
        username.addValidator(new MinLengthValidator(5));
        username.addValidator(new MaxLengthValidator(40));
        firstname.setAllowBlank(false);
        firstname.addValidator(new MinLengthValidator(3));
        firstname.addValidator(new MaxLengthValidator(40));
        lastname.setAllowBlank(false);
        lastname.addValidator(new MinLengthValidator(3));
        lastname.addValidator(new MaxLengthValidator(40));
        password.setAllowBlank(false);
        password.addValidator(new MinLengthValidator(8));
        password.addValidator(new MaxLengthValidator(30));
        confirmPassword.setAllowBlank(false);
        confirmPassword.addValidator(new MinLengthValidator(8));
        confirmPassword.addValidator(new MaxLengthValidator(30));
        email.setAllowBlank(false);
        email.addValidator(new MinLengthValidator(5));
        email.addValidator(new MaxLengthValidator(40));
        email.addValidator(new RegExValidator("^[a-z0-9]+@[a-z0-9].[a-z0-9]+", "aa"));
        dob.setAllowBlank(false);
        dob.addValidator(new MaxDateValidator(new Date()));
    }

    @Override
    public HasSelectHandlers getSendButton() {
        return send;
    }

    @Override
    public boolean validate() {
        return mainPanel.isValid();
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
