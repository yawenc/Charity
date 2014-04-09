package com.bertazoli.charity.client.application;

import com.bertazoli.charity.client.application.charity.RegisterCharityModule;
import com.bertazoli.charity.client.application.home.HomeModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.bertazoli.charity.client.application.login.LoginModule;
import com.bertazoli.charity.client.application.main.MainModule;
import com.bertazoli.charity.client.application.usersettings.UserSettingsModule;
import com.bertazoli.charity.client.application.signup.SignupModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new RegisterCharityModule());
        install(new SignupModule());
        install(new UserSettingsModule());
        install(new MainModule());
        install(new LoginModule());
        install(new HomeModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
