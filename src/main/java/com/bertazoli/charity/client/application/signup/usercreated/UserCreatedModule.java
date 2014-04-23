package com.bertazoli.charity.client.application.signup.usercreated;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserCreatedModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(UserCreatedPresenter.class, UserCreatedPresenter.MyView.class, UserCreatedView.class, UserCreatedPresenter.MyProxy.class);
    }
}
