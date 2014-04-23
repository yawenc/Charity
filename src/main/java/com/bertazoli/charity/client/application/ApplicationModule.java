package com.bertazoli.charity.client.application;

import com.bertazoli.charity.client.application.charity.RegisterCharityModule;
import com.bertazoli.charity.client.application.home.HomeModule;
import com.bertazoli.charity.client.application.login.LoginModule;
import com.bertazoli.charity.client.application.main.MainModule;
import com.bertazoli.charity.client.application.signup.SignupModule;
import com.bertazoli.charity.client.application.signup.usercreated.UserCreatedModule;
import com.bertazoli.charity.client.application.usersettings.UserSettingsModule;
import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;

public class ApplicationModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
        install(new RegisterCharityModule());
        install(new SignupModule());
        install(new UserSettingsModule());
        install(new MainModule());
        install(new LoginModule());
        install(new HomeModule());
        install(new UserCreatedModule());

        bindPresenter(ApplicationPresenter.class, ApplicationPresenter.MyView.class, ApplicationView.class,
                ApplicationPresenter.MyProxy.class);
    }
}
