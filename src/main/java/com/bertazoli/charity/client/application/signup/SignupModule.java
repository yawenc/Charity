package com.bertazoli.charity.client.application.signup;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class SignupModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(SignupPresenter.class, SignupPresenter.MyView.class, SignupView.class, SignupPresenter.MyProxy.class);
    }
}
