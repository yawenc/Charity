package com.bertazoli.charity.client.application.usersettings;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class UserSettingsModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        bindPresenter(UserSettingsPresenter.class, UserSettingsPresenter.MyView.class, UserSettingsView.class, UserSettingsPresenter.MyProxy.class);
    }
}
